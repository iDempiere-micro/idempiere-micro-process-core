package org.compiere.process;

import kotliquery.Row;
import org.compiere.orm.MRole;
import org.compiere.orm.MRoleKt;

/**
 * Process Access Model
 *
 * @author Jorg Janke
 * @version $Id: MProcessAccess.java,v 1.3 2006/07/30 00:58:04 jjanke Exp $
 */
public class MProcessAccess extends X_AD_Process_Access {

    /**
     *
     */
    private static final long serialVersionUID = -2468108979800832171L;

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param ignored ignored
     */
    public MProcessAccess(int ignored) {
        super(0);
        if (ignored != 0) throw new IllegalArgumentException("Multi-Key");
        else {
            setIsReadWrite(true);
        }
    } //	MProcessAccess

    /**
     * Load Constructor
     */
    public MProcessAccess(Row row) {
        super(row);
    } //	MProcessAccess

    /**
     * Parent Constructor
     *
     * @param parent     parent
     * @param AD_Role_ID role id
     */
    public MProcessAccess(MProcess parent, int AD_Role_ID) {
        super(0);
        MRole role = MRoleKt.getRole(AD_Role_ID);
        setClientOrg(role);
        setProcessId(parent.getProcessId());
        setRoleId(AD_Role_ID);
    } //	MProcessAccess
} //	MProcessAccess
