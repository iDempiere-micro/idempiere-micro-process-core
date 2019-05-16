package org.compiere.process;

import kotliquery.Row;
import org.compiere.model.ProcessParameter;
import org.compiere.orm.BasePOName;

/**
 * Generated Model for AD_Process_Para
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_AD_Process_Para extends BasePOName implements ProcessParameter {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_AD_Process_Para(int AD_Process_Para_ID) {
        super(AD_Process_Para_ID);
        /**
         * if (AD_Process_Para_ID == 0) { setAD_Process_ID (0); setAD_Process_Para_ID (0);
         * setAD_Reference_ID (0); setColumnName (null); setEntityType (null); // @SQL=select
         * get_sysconfig('DEFAULT_ENTITYTYPE','U',0,0) from dual setFieldLength (0);
         * setIsCentrallyMaintained (true); // Y setIsEncrypted (false); // N setIsMandatory (false);
         * setIsRange (false); setName (null); setSeqNo (0); // @SQL=SELECT NVL(MAX(SeqNo),0)+10 AS
         * DefaultValue FROM AD_Process_Para WHERE AD_Process_ID=@AD_Process_ID@ }
         */
    }

    /**
     * Load Constructor
     */
    public X_AD_Process_Para(Row row) {
        super(row);
    }

    /**
     * AccessLevel
     *
     * @return 4 - System
     */
    protected int getAccessLevel() {
        return accessLevel.intValue();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_AD_Process_Para[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get System Element.
     *
     * @return System Element enables the central maintenance of column description and help.
     */
    public int getElementId() {
        Integer ii = getValue(COLUMNNAME_AD_Element_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set System Element.
     *
     * @param AD_Element_ID System Element enables the central maintenance of column description and
     *                      help.
     */
    public void setElementId(int AD_Element_ID) {
        if (AD_Element_ID < 1) setValue(COLUMNNAME_AD_Element_ID, null);
        else setValue(COLUMNNAME_AD_Element_ID, Integer.valueOf(AD_Element_ID));
    }

    /**
     * Set Process.
     *
     * @param AD_Process_ID Process or Report
     */
    public void setProcessId(int AD_Process_ID) {
        if (AD_Process_ID < 1) setValueNoCheck(COLUMNNAME_AD_Process_ID, null);
        else setValueNoCheck(COLUMNNAME_AD_Process_ID, Integer.valueOf(AD_Process_ID));
    }

    /**
     * Get Process Parameter.
     *
     * @return Process Parameter
     */
    public int getProcessParameterId() {
        Integer ii = getValue(COLUMNNAME_AD_Process_Para_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Reference.
     *
     * @return System Reference and Validation
     */
    public int getReferenceId() {
        Integer ii = getValue(COLUMNNAME_AD_Reference_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Reference.
     *
     * @param AD_Reference_ID System Reference and Validation
     */
    public void setReferenceId(int AD_Reference_ID) {
        if (AD_Reference_ID < 1) setValue(COLUMNNAME_AD_Reference_ID, null);
        else setValue(COLUMNNAME_AD_Reference_ID, Integer.valueOf(AD_Reference_ID));
    }

    /**
     * Get Reference Key.
     *
     * @return Required to specify, if data type is Table or List
     */
    public int getReferenceValueId() {
        Integer ii = getValue(COLUMNNAME_AD_Reference_Value_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Reference Key.
     *
     * @param AD_Reference_Value_ID Required to specify, if data type is Table or List
     */
    public void setReferenceValueId(int AD_Reference_Value_ID) {
        if (AD_Reference_Value_ID < 1) setValue(COLUMNNAME_AD_Reference_Value_ID, null);
        else setValue(COLUMNNAME_AD_Reference_Value_ID, Integer.valueOf(AD_Reference_Value_ID));
    }

    /**
     * Set Dynamic Validation.
     *
     * @param AD_Val_Rule_ID Dynamic Validation Rule
     */
    public void setAdValRuleId(int AD_Val_Rule_ID) {
        if (AD_Val_Rule_ID < 1) setValue(COLUMNNAME_AD_Val_Rule_ID, null);
        else setValue(COLUMNNAME_AD_Val_Rule_ID, Integer.valueOf(AD_Val_Rule_ID));
    }

    /**
     * Get Dynamic Validation.
     *
     * @return Dynamic Validation Rule
     */
    public int getValRuleId() {
        Integer ii = getValue(COLUMNNAME_AD_Val_Rule_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get DB Column Name.
     *
     * @return Name of the column in the database
     */
    public String getColumnName() {
        return getValue(COLUMNNAME_ColumnName);
    }

    /**
     * Set DB Column Name.
     *
     * @param ColumnName Name of the column in the database
     */
    public void setColumnName(String ColumnName) {
        setValue(COLUMNNAME_ColumnName, ColumnName);
    }

    /**
     * Get Default Logic.
     *
     * @return Default value hierarchy, separated by ;
     */
    public String getDefaultValue() {
        return getValue(COLUMNNAME_DefaultValue);
    }

    /**
     * Set Default Logic.
     *
     * @param DefaultValue Default value hierarchy, separated by ;
     */
    public void setDefaultValue(String DefaultValue) {
        setValue(COLUMNNAME_DefaultValue, DefaultValue);
    }

    /**
     * Get Default Logic 2.
     *
     * @return Default value hierarchy, separated by ;
     */
    public String getDefaultValue2() {
        return getValue(COLUMNNAME_DefaultValue2);
    }

    /**
     * Set Default Logic 2.
     *
     * @param DefaultValue2 Default value hierarchy, separated by ;
     */
    public void setDefaultValue2(String DefaultValue2) {
        setValue(COLUMNNAME_DefaultValue2, DefaultValue2);
    }

    /**
     * Get Description.
     *
     * @return Optional short description of the record
     */
    public String getDescription() {
        return getValue(COLUMNNAME_Description);
    }

    /**
     * Set Description.
     *
     * @param Description Optional short description of the record
     */
    public void setDescription(String Description) {
        setValue(COLUMNNAME_Description, Description);
    }

    /**
     * Get Display Logic.
     *
     * @return If the Field is displayed, the result determines if the field is actually displayed
     */
    public String getDisplayLogic() {
        return getValue(COLUMNNAME_DisplayLogic);
    }

    /**
     * Set Display Logic.
     *
     * @param DisplayLogic If the Field is displayed, the result determines if the field is actually
     *                     displayed
     */
    public void setDisplayLogic(String DisplayLogic) {
        setValue(COLUMNNAME_DisplayLogic, DisplayLogic);
    }

    /**
     * Set Entity Type.
     *
     * @param EntityType Dictionary Entity Type; Determines ownership and synchronization
     */
    public void setEntityType(String EntityType) {

        setValue(COLUMNNAME_EntityType, EntityType);
    }

    /**
     * Get Length.
     *
     * @return Length of the column in the database
     */
    public int getFieldLength() {
        Integer ii = getValue(COLUMNNAME_FieldLength);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Length.
     *
     * @param FieldLength Length of the column in the database
     */
    public void setFieldLength(int FieldLength) {
        setValue(COLUMNNAME_FieldLength, Integer.valueOf(FieldLength));
    }

    /**
     * Get Comment/Help.
     *
     * @return Comment or Hint
     */
    public String getHelp() {
        return getValue(COLUMNNAME_Help);
    }

    /**
     * Set Comment/Help.
     *
     * @param Help Comment or Hint
     */
    public void setHelp(String Help) {
        setValue(COLUMNNAME_Help, Help);
    }

    /**
     * Set Centrally maintained.
     *
     * @param IsCentrallyMaintained Information maintained in System Element table
     */
    public void setIsCentrallyMaintained(boolean IsCentrallyMaintained) {
        setValue(COLUMNNAME_IsCentrallyMaintained, Boolean.valueOf(IsCentrallyMaintained));
    }

    /**
     * Get Centrally maintained.
     *
     * @return Information maintained in System Element table
     */
    public boolean isCentrallyMaintained() {
        Object oo = getValue(COLUMNNAME_IsCentrallyMaintained);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Set Mandatory.
     *
     * @param IsMandatory Data entry is required in this column
     */
    public void setIsMandatory(boolean IsMandatory) {
        setValue(COLUMNNAME_IsMandatory, Boolean.valueOf(IsMandatory));
    }

    /**
     * Get Mandatory.
     *
     * @return Data entry is required in this column
     */
    public boolean isMandatory() {
        Object oo = getValue(COLUMNNAME_IsMandatory);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Set Range.
     *
     * @param IsRange The parameter is a range of values
     */
    public void setIsRange(boolean IsRange) {
        setValue(COLUMNNAME_IsRange, Boolean.valueOf(IsRange));
    }

    /**
     * Get Range.
     *
     * @return The parameter is a range of values
     */
    public boolean isRange() {
        Object oo = getValue(COLUMNNAME_IsRange);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Get Read Only Logic.
     *
     * @return Logic to determine if field is read only (applies only when field is read-write)
     */
    public String getReadOnlyLogic() {
        return getValue(COLUMNNAME_ReadOnlyLogic);
    }

    /**
     * Set Read Only Logic.
     *
     * @param ReadOnlyLogic Logic to determine if field is read only (applies only when field is
     *                      read-write)
     */
    public void setReadOnlyLogic(String ReadOnlyLogic) {
        setValue(COLUMNNAME_ReadOnlyLogic, ReadOnlyLogic);
    }

    /**
     * Get Sequence.
     *
     * @return Method of ordering records; lowest number comes first
     */
    public int getSeqNo() {
        Integer ii = getValue(COLUMNNAME_SeqNo);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Sequence.
     *
     * @param SeqNo Method of ordering records; lowest number comes first
     */
    public void setSeqNo(int SeqNo) {
        setValue(COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
    }

    /**
     * Get Max. Value.
     *
     * @return Maximum Value for a field
     */
    public String getValueMax() {
        return getValue(COLUMNNAME_ValueMax);
    }

    /**
     * Set Max. Value.
     *
     * @param ValueMax Maximum Value for a field
     */
    public void setValueMax(String ValueMax) {
        setValue(COLUMNNAME_ValueMax, ValueMax);
    }

    /**
     * Get Min. Value.
     *
     * @return Minimum Value for a field
     */
    public String getValueMin() {
        return getValue(COLUMNNAME_ValueMin);
    }

    /**
     * Set Min. Value.
     *
     * @param ValueMin Minimum Value for a field
     */
    public void setValueMin(String ValueMin) {
        setValue(COLUMNNAME_ValueMin, ValueMin);
    }

    /**
     * Get Value Format.
     *
     * @return Format of the value; Can contain fixed format elements, Variables: "_lLoOaAcCa09"
     */
    public String getVFormat() {
        return getValue(COLUMNNAME_VFormat);
    }

    /**
     * Set Value Format.
     *
     * @param VFormat Format of the value; Can contain fixed format elements, Variables:
     *                "_lLoOaAcCa09"
     */
    public void setVFormat(String VFormat) {
        setValue(COLUMNNAME_VFormat, VFormat);
    }

    @Override
    public int getTableId() {
        return ProcessParameter.Table_ID;
    }

}
