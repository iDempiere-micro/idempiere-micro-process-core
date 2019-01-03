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

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_AD_Rule(Properties ctx, int AD_Rule_ID, String trxName) {
    super(ctx, AD_Rule_ID, trxName);
    /**
     * if (AD_Rule_ID == 0) { setAD_Rule_ID (0); setEntityType (null); // @SQL=select
     * get_sysconfig('DEFAULT_ENTITYTYPE','U',0,0) from dual setEventType (null); setName (null);
     * setRuleType (null); setValue (null); }
     */
  }

  /** Load Constructor */
  public X_AD_Rule(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
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

  /** AccessLevel AD_Reference_ID=5 */
  public static final int ACCESSLEVEL_AD_Reference_ID = 5;
  /** Organization = 1 */
  public static final String ACCESSLEVEL_Organization = "1";
  /** Client+Organization = 3 */
  public static final String ACCESSLEVEL_ClientPlusOrganization = "3";
  /** System only = 4 */
  public static final String ACCESSLEVEL_SystemOnly = "4";
  /** All = 7 */
  public static final String ACCESSLEVEL_All = "7";
  /** System+Client = 6 */
  public static final String ACCESSLEVEL_SystemPlusClient = "6";
  /** Client only = 2 */
  public static final String ACCESSLEVEL_ClientOnly = "2";
  /**
   * Set Data Access Level.
   *
   * @param AccessLevel Access Level required
   */
  public void setRuleAccessLevel(String AccessLevel) {

    set_Value(I_AD_Rule.COLUMNNAME_AccessLevel, AccessLevel);
  }

  /**
   * Get Data Access Level.
   *
   * @return Access Level required
   */
  public String getRuleAccessLevel() {
    return (String) get_Value(I_AD_Rule.COLUMNNAME_AccessLevel);
  }

  /**
   * Set Rule.
   *
   * @param AD_Rule_ID Rule
   */
  public void setAD_Rule_ID(int AD_Rule_ID) {
    if (AD_Rule_ID < 1) set_ValueNoCheck(I_AD_Rule.COLUMNNAME_AD_Rule_ID, null);
    else set_ValueNoCheck(I_AD_Rule.COLUMNNAME_AD_Rule_ID, Integer.valueOf(AD_Rule_ID));
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
   * Set AD_Rule_UU.
   *
   * @param AD_Rule_UU AD_Rule_UU
   */
  public void setAD_Rule_UU(String AD_Rule_UU) {
    set_Value(I_AD_Rule.COLUMNNAME_AD_Rule_UU, AD_Rule_UU);
  }

  /**
   * Get AD_Rule_UU.
   *
   * @return AD_Rule_UU
   */
  public String getAD_Rule_UU() {
    return (String) get_Value(I_AD_Rule.COLUMNNAME_AD_Rule_UU);
  }

  /**
   * Set Description.
   *
   * @param Description Optional short description of the record
   */
  public void setDescription(String Description) {
    set_Value(I_AD_Rule.COLUMNNAME_Description, Description);
  }

  /**
   * Get Description.
   *
   * @return Optional short description of the record
   */
  public String getDescription() {
    return (String) get_Value(I_AD_Rule.COLUMNNAME_Description);
  }

  /** EntityType AD_Reference_ID=389 */
  public static final int ENTITYTYPE_AD_Reference_ID = 389;
  /**
   * Set Entity Type.
   *
   * @param EntityType Dictionary Entity Type; Determines ownership and synchronization
   */
  public void setEntityType(String EntityType) {

    set_Value(I_AD_Rule.COLUMNNAME_EntityType, EntityType);
  }

  /**
   * Get Entity Type.
   *
   * @return Dictionary Entity Type; Determines ownership and synchronization
   */
  public String getEntityType() {
    return (String) get_Value(I_AD_Rule.COLUMNNAME_EntityType);
  }

  /** EventType AD_Reference_ID=53236 */
  public static final int EVENTTYPE_AD_Reference_ID = 53236;
  /** Callout = C */
  public static final String EVENTTYPE_Callout = "C";
  /** Process = P */
  public static final String EVENTTYPE_Process = "P";
  /** Model Validator Table Event = T */
  public static final String EVENTTYPE_ModelValidatorTableEvent = "T";
  /** Model Validator Document Event = D */
  public static final String EVENTTYPE_ModelValidatorDocumentEvent = "D";
  /** Model Validator Login Event = L */
  public static final String EVENTTYPE_ModelValidatorLoginEvent = "L";
  /** Human Resource & Payroll = H */
  public static final String EVENTTYPE_HumanResourcePayroll = "H";
  /** Measure for Performance Analysis = M */
  public static final String EVENTTYPE_MeasureForPerformanceAnalysis = "M";
  /** GL Reconciliation = R */
  public static final String EVENTTYPE_GLReconciliation = "R";
  /**
   * Set Event Type.
   *
   * @param EventType Type of Event
   */
  public void setEventType(String EventType) {

    set_Value(I_AD_Rule.COLUMNNAME_EventType, EventType);
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
   * Set Comment/Help.
   *
   * @param Help Comment or Hint
   */
  public void setHelp(String Help) {
    set_Value(I_AD_Rule.COLUMNNAME_Help, Help);
  }

  /**
   * Get Comment/Help.
   *
   * @return Comment or Hint
   */
  public String getHelp() {
    return (String) get_Value(I_AD_Rule.COLUMNNAME_Help);
  }

  /** RuleType AD_Reference_ID=53235 */
  public static final int RULETYPE_AD_Reference_ID = 53235;
  /** Aspect Orient Program = A */
  public static final String RULETYPE_AspectOrientProgram = "A";
  /** JSR 223 Scripting APIs = S */
  public static final String RULETYPE_JSR223ScriptingAPIs = "S";
  /** JSR 94 Rule Engine API = R */
  public static final String RULETYPE_JSR94RuleEngineAPI = "R";
  /** SQL = Q */
  public static final String RULETYPE_SQL = "Q";
  /**
   * Set Rule Type.
   *
   * @param RuleType Rule Type
   */
  public void setRuleType(String RuleType) {

    set_Value(I_AD_Rule.COLUMNNAME_RuleType, RuleType);
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
   * Set Script.
   *
   * @param Script Dynamic Java Language Script to calculate result
   */
  public void setScript(String Script) {
    set_Value(I_AD_Rule.COLUMNNAME_Script, Script);
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
