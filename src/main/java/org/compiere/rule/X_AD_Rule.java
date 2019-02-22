package org.compiere.rule;

import org.compiere.model.I_AD_Rule;
import org.compiere.orm.BasePONameValue;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for AD_Rule
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_Rule extends BasePONameValue implements I_AD_Rule, I_Persistent {

    /**
     * Process = P
     */
    public static final String EVENTTYPE_Process = "P";
    /**
     * Model Validator Table Event = T
     */
    public static final String EVENTTYPE_ModelValidatorTableEvent = "T";
    /**
     * Model Validator Document Event = D
     */
    public static final String EVENTTYPE_ModelValidatorDocumentEvent = "D";
    /**
     * Model Validator Login Event = L
     */
    public static final String EVENTTYPE_ModelValidatorLoginEvent = "L";
    /**
     * Measure for Performance Analysis = M
     */
    public static final String EVENTTYPE_MeasureForPerformanceAnalysis = "M";
    /**
     * JSR 223 Scripting APIs = S
     */
    public static final String RULETYPE_JSR223ScriptingAPIs = "S";
    /**
     *
     */
    private static final long serialVersionUID = 20171031L;
    /**
     * Standard Constructor
     */
    public X_AD_Rule(Properties ctx, int AD_Rule_ID) {
        super(ctx, AD_Rule_ID);
        /**
         * if (AD_Rule_ID == 0) { setAD_Rule_ID (0); setEntityType (null); // @SQL=select
         * get_sysconfig('DEFAULT_ENTITYTYPE','U',0,0) from dual setEventType (null); setName (null);
         * setRuleType (null); setValue (null); }
         */
    }
    /**
     * Load Constructor
     */
    public X_AD_Rule(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    /**
     * AccessLevel
     *
     * @return 4 - System
     */
    protected int getAccessLevel() {
        return I_AD_Rule.accessLevel.intValue();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_AD_Rule[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Rule.
     *
     * @return Rule
     */
    public int getAD_Rule_ID() {
        Integer ii = (Integer) get_Value(I_AD_Rule.COLUMNNAME_AD_Rule_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Event Type.
     *
     * @return Type of Event
     */
    public String getEventType() {
        return (String) get_Value(I_AD_Rule.COLUMNNAME_EventType);
    }

    /**
     * Get Rule Type.
     *
     * @return Rule Type
     */
    public String getRuleType() {
        return (String) get_Value(I_AD_Rule.COLUMNNAME_RuleType);
    }

    /**
     * Get Script.
     *
     * @return Dynamic Java Language Script to calculate result
     */
    public String getScript() {
        return (String) get_Value(I_AD_Rule.COLUMNNAME_Script);
    }

    @Override
    public int getTableId() {
        return I_AD_Rule.Table_ID;
    }
}
