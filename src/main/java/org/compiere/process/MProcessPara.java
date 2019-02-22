package org.compiere.process;

import org.compiere.model.I_AD_Process_Para;
import org.compiere.orm.M_Element;
import org.idempiere.common.util.CCache;
import org.idempiere.orm.PO;

import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.executeUpdateEx;

/**
 * Process Parameter Model
 *
 * @author Jorg Janke
 * @version $Id: MProcessPara.java,v 1.3 2006/07/30 00:58:37 jjanke Exp $
 */
public class MProcessPara extends X_AD_Process_Para {
    /**
     *
     */
    private static final long serialVersionUID = 4580303034897910371L;
    /**
     * Cache
     */
    private static CCache<Integer, MProcessPara> s_cache =
            new CCache<Integer, MProcessPara>(I_AD_Process_Para.Table_Name, 20);

    /**
     * ************************************************************************ Constructor
     *
     * @param ctx                context
     * @param AD_Process_Para_ID id
     * @param trxName            transaction
     */
    public MProcessPara(Properties ctx, int AD_Process_Para_ID) {
        super(ctx, AD_Process_Para_ID);
        if (AD_Process_Para_ID == 0) {
            //	setAD_Process_ID (0);	Parent
            //	setName (null);
            //	setColumnName (null);

            setFieldLength(0);
            setSeqNo(0);
            //	setAD_Reference_ID (0);
            setIsCentrallyMaintained(true);
            setIsRange(false);
            setIsMandatory(false);
            setEntityType(PO.ENTITYTYPE_UserMaintained);
        }
    } //	MProcessPara

    /**
     * Load Constructor
     *
     * @param ctx     context
     * @param rs      result set
     * @param trxName transaction
     */
    public MProcessPara(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    } //	MProcessPara

    /**
     * Parent constructor
     *
     * @param parent process
     */
    public MProcessPara(MProcess parent) {

        this(parent.getCtx(), 0);
        setClientOrg(parent);
        setAD_Process_ID(parent.getAD_Process_ID());
        setEntityType(parent.getEntityType());
    }

    /**
     * Get MProcessPara from Cache
     *
     * @param ctx                context
     * @param AD_Process_Para_ID id
     * @return MProcessPara
     */
    public static MProcessPara get(Properties ctx, int AD_Process_Para_ID) {
        Integer key = new Integer(AD_Process_Para_ID);
        MProcessPara retValue = (MProcessPara) s_cache.get(key);
        if (retValue != null) return retValue;
        retValue = new MProcessPara(ctx, AD_Process_Para_ID);
        if (retValue.getId() != 0) s_cache.put(key, retValue);
        return retValue;
    } //	get

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuffer sb = new StringBuffer("MProcessPara[").append(getId()).append("]");
        return sb.toString();
    } //	toString

    /**
     * Copy settings from another process parameter overwrites existing data (including translations)
     * and saves
     *
     * @param source
     */
    public void copyFrom(MProcessPara source) {

        if (log.isLoggable(Level.FINE)) log.log(Level.FINE, "Copying from:" + source + ", to: " + this);
        setAD_Element_ID(source.getAD_Element_ID());
        setAD_Reference_ID(source.getReferenceId());
        setAD_Reference_Value_ID(source.getAD_Reference_Value_ID());
        setAD_Val_Rule_ID(source.getValRule_ID());
        setColumnName(source.getColumnName());
        setDefaultValue(source.getDefaultValue());
        setDefaultValue2(source.getDefaultValue2());
        setDescription(source.getDescription());
        setDisplayLogic(source.getDisplayLogic());
        setFieldLength(source.getFieldLength());
        setHelp(source.getHelp());
        setIsActive(source.isActive());
        setIsCentrallyMaintained(source.isCentrallyMaintained());
        setIsMandatory(source.isMandatory());
        setIsRange(source.isRange());
        setName(source.getName());
        setReadOnlyLogic(source.getReadOnlyLogic());
        setSeqNo(source.getSeqNo());
        setValueMax(source.getValueMax());
        setValueMin(source.getValueMin());
        setVFormat(source.getVFormat());

        saveEx();

        // delete new translations and copy translations from source
        String sql = "DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = ?";
        int count = executeUpdateEx(sql, new Object[]{getAD_Process_Para_ID()});
        if (log.isLoggable(Level.FINE)) log.log(Level.FINE, "AD_Process_Para_Trl deleted: " + count);

        sql =
                "INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language, "
                        + " AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, "
                        + " Name, Description, Help, IsTranslated) "
                        + " SELECT ?, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, "
                        + " Updated, UpdatedBy, Name, Description, Help, IsTranslated "
                        + " FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = ? ";
        count =
                executeUpdateEx(
                        sql,
                        new Object[]{getAD_Process_Para_ID(), source.getAD_Process_Para_ID()}
                );
        if (log.isLoggable(Level.FINE)) log.log(Level.FINE, "AD_Process_Para_Trl inserted: " + count);
    }

    /**
     * ************************************************************************ Before Save
     *
     * @param newRecord
     * @return save
     */
    protected boolean beforeSave(boolean newRecord) {
        if (isCentrallyMaintained() && getAD_Element_ID() == 0)
            setIsCentrallyMaintained(
                    false); // IDEMPIERE 109 - param without element can't be centrally maintained

        //	Sync Terminology
        if ((newRecord || is_ValueChanged("AD_Element_ID"))
                && getAD_Element_ID() != 0
                && isCentrallyMaintained()) {
            M_Element element = new M_Element(getCtx(), getAD_Element_ID());
            setColumnName(element.getColumnName());
            setName(element.getName());
            setDescription(element.getDescription());
            setHelp(element.getHelp());
        }

        return true;
    } //	beforeSave

} //	MProcessPara
