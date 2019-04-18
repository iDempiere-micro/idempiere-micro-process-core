package org.compiere.rule;

import kotliquery.Row;
import org.compiere.model.I_AD_Rule;
import org.compiere.orm.Query;
import org.compiere.util.MsgKt;
import org.idempiere.common.util.CCache;
import org.idempiere.common.util.CLogger;
import org.idempiere.common.util.Util;

import javax.script.ScriptEngine;
import java.util.Enumeration;
import java.util.Iterator;

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
    private static CCache<Integer, I_AD_Rule> s_cache =
            new CCache<>(I_AD_Rule.Table_Name, 20);
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
     * @param AD_Rule_ID id
     */
    public MRule(int AD_Rule_ID) {
        super(AD_Rule_ID);
    } //	MRule

    /**
     * Load Constructor
     */
    public MRule(Row row) {
        super(row);
    } //	MRule

    /**
     * Get Rule from Cache
     *
     * @param AD_Rule_ID id
     * @return MRule
     */
    public static I_AD_Rule get(int AD_Rule_ID) {
        Integer key = AD_Rule_ID;
        I_AD_Rule retValue = s_cache.get(key);
        if (retValue != null) return retValue;
        retValue = new MRule(AD_Rule_ID);
        if (retValue.getId() != 0) s_cache.put(key, retValue);
        return retValue;
    } //	get

    /**
     * Get Rule from Cache
     *
     * @param ruleValue case sensitive rule Value
     * @return Rule
     */
    public static I_AD_Rule get(String ruleValue) {
        if (ruleValue == null) return null;
        for (I_AD_Rule retValue : s_cache.values()) {
            if (ruleValue.equals(retValue.getSearchKey())) return retValue;
        }
        //
        final String whereClause = "Value=?";
        I_AD_Rule retValue =
                new Query<I_AD_Rule>(I_AD_Rule.Table_Name, whereClause)
                        .setParameters(ruleValue)
                        .setOnlyActiveRecords(true)
                        .first();

        if (retValue != null) {
            Integer key = retValue.getRuleId();
            s_cache.put(key, retValue);
        }
        return retValue;
    } //	get

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
                log.saveError("Error", MsgKt.getMsg("WrongScriptValue"));
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
