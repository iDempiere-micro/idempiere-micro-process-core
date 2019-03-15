package org.compiere.rule;

import kotliquery.Row;
import org.compiere.model.I_AD_Rule;
import org.compiere.orm.Query;
import org.compiere.util.Msg;
import org.idempiere.common.util.CCache;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Util;

import javax.script.ScriptEngine;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

/**
 * Persistent Rule Model
 *
 * @author Carlos Ruiz
 * @author Silvano Trinchero, www.freepath.it
 * <li>IDEMPIERE-3243 refactored getScriptEngine to use Core.getScriptEngine
 * @version $Id: MRule.java
 */
public class MRule extends X_AD_Rule {
    // global or login context variable prefix
    public static final String GLOBAL_CONTEXT_PREFIX = "G_";
    // window context variable prefix
    public static final String WINDOW_CONTEXT_PREFIX = "W_";
    // method call arguments prefix
    public static final String ARGUMENTS_PREFIX = "A_";
    // process parameters prefix
    public static final String PARAMETERS_PREFIX = "P_";
    public static final String SCRIPT_PREFIX = "@script:";
    /**
     *
     */
    private static final long serialVersionUID = -9166262780531877045L;
    /**
     * Cache
     */
    private static CCache<Integer, MRule> s_cache =
            new CCache<Integer, MRule>(I_AD_Rule.Table_Name, 20);
    /**
     * Static Logger
     */
    @SuppressWarnings("unused")
    private static CLogger s_log = CLogger.getCLogger(MRule.class);
    /* The Engine */
    ScriptEngine engine = null;

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param ctx        context
     * @param AD_Rule_ID id
     * @param trxName    transaction
     */
    public MRule(Properties ctx, int AD_Rule_ID) {
        super(ctx, AD_Rule_ID);
    } //	MRule

    /**
     * Load Constructor
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName transaction
     */
    public MRule(Properties ctx, Row row) {
        super(ctx, row);
    } //	MRule

    /**
     * Get Rule from Cache
     *
     * @param ctx        context
     * @param AD_Rule_ID id
     * @return MRule
     */
    public static MRule get(Properties ctx, int AD_Rule_ID) {
        Integer key = AD_Rule_ID;
        MRule retValue = s_cache.get(key);
        if (retValue != null) return retValue;
        retValue = new MRule(ctx, AD_Rule_ID);
        if (retValue.getId() != 0) s_cache.put(key, retValue);
        return retValue;
    } //	get

    /**
     * Get Rule from Cache
     *
     * @param ctx       context
     * @param ruleValue case sensitive rule Value
     * @return Rule
     */
    public static MRule get(Properties ctx, String ruleValue) {
        if (ruleValue == null) return null;
        Iterator<MRule> it = s_cache.values().iterator();
        while (it.hasNext()) {
            MRule retValue = it.next();
            if (ruleValue.equals(retValue.getSearchKey())) return retValue;
        }
        //
        final String whereClause = "Value=?";
        MRule retValue =
                new Query(ctx, I_AD_Rule.Table_Name, whereClause)
                        .setParameters(ruleValue)
                        .setOnlyActiveRecords(true)
                        .first();

        if (retValue != null) {
            Integer key = new Integer(retValue.getRuleId());
            s_cache.put(key, retValue);
        }
        return retValue;
    } //	get

    /**
     * ************************************************************************ Set Context ctx to the
     * engine based on windowNo
     *
     * @param engine   ScriptEngine
     * @param ctx      context
     * @param windowNo window number
     */
    public static void setContext(ScriptEngine engine, Properties ctx, int windowNo) {
        Enumeration<Object> en = ctx.keys();
        while (en.hasMoreElements()) {
            String key = en.nextElement().toString();
            //  filter
            if (key == null
                    || key.length() == 0
                    || key.startsWith("P") //  Preferences
                    || (key.indexOf('|') != -1
                    && !key.startsWith(String.valueOf(windowNo))) //  other Window Settings
                    || (key.indexOf('|') != -1 && key.indexOf('|') != key.lastIndexOf('|')) // other tab
            ) continue;
            Object value = ctx.get(key);
            if (value != null) {
                if (value instanceof Boolean)
                    engine.put(convertKey(key, windowNo), ((Boolean) value).booleanValue());
                else if (value instanceof Integer)
                    engine.put(convertKey(key, windowNo), ((Integer) value).intValue());
                else if (value instanceof Double)
                    engine.put(convertKey(key, windowNo), ((Double) value).doubleValue());
                else engine.put(convertKey(key, windowNo), value);
            }
        }
    }

    /**
     * Convert Key # -> _
     *
     * @param key
     * @param m_windowNo
     * @return converted key
     */
    public static String convertKey(String key, int m_windowNo) {
        String k = m_windowNo + "|";
        if (key.startsWith(k)) {
            String retValue = WINDOW_CONTEXT_PREFIX + key.substring(k.length());
            retValue = Util.replace(retValue, "|", "_");
            return retValue;
        } else {
            String retValue = null;
            if (key.startsWith("#")) retValue = GLOBAL_CONTEXT_PREFIX + key.substring(1);
            else retValue = key;
            retValue = Util.replace(retValue, "#", "_");
            return retValue;
        }
    } //  convertKey

    /**
     * Before Save
     *
     * @param newRecord new
     * @return true
     */
    protected boolean beforeSave(boolean newRecord) {
        // Validate format for scripts
        // must be engine:name
        // where engine can be groovy, jython or beanshell
        if (getRuleType().equals(X_AD_Rule.RULETYPE_JSR223ScriptingAPIs)) {
            String engineName = getEngineName();
            if (engineName == null
                    || (!(engineName.equalsIgnoreCase("groovy")
                    || engineName.equalsIgnoreCase("jython")
                    || engineName.equalsIgnoreCase("beanshell")))) {
                log.saveError("Error", Msg.getMsg(getCtx(), "WrongScriptValue"));
                return false;
            }
        }
        return true;
    } //	beforeSave

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("MRule[");
        sb.append(getId()).append("-").append(getSearchKey()).append("]");
        return sb.toString();
    } //	toString

    /**
     * Script Engine for this rule
     *
     * @return ScriptEngine
     */
    public ScriptEngine getScriptEngine() {

        String engineName = getEngineName();

        if (engineName != null) engine = ScriptEngineWrapper.getScriptEngine();

        return engine;
    }

    public String getEngineName() {
        int colonPosition = getSearchKey().indexOf(":");
        if (colonPosition < 0) return null;
        return getSearchKey().substring(0, colonPosition);
    }
} //	MRule
