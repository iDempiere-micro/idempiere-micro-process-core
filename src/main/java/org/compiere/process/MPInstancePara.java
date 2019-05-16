package org.compiere.process;

import kotliquery.Row;

import java.math.BigDecimal;

/**
 * Process Instance Parameter Model
 *
 * @author Jorg Janke
 * @version $Id: MPInstancePara.java,v 1.3 2006/07/30 00:58:05 jjanke Exp $
 */
public class MPInstancePara extends X_AD_PInstance_Para {

    /**
     *
     */
    private static final long serialVersionUID = -8407658637240252680L;

    /**
     * Persistence Constructor
     *
     * @param ctx     context
     * @param ignored ignored
     */
    public MPInstancePara(int ignored) {
        super(0);
        if (ignored != 0) throw new IllegalArgumentException("Multi-Key");
    } //	MPInstance_Para

    /**
     * Parent Constructor
     *
     * @param ctx
     * @param AD_PInstance_ID id
     * @param SeqNo           sequence
     */
    public MPInstancePara(int AD_PInstance_ID, int SeqNo) {
        super(0);
        setPInstanceId(AD_PInstance_ID);
        setSeqNo(SeqNo);
    } //	MPInstance_Para

    /**
     * Parent Constructor
     *
     * @param instance instance
     * @param SeqNo    sequence
     */
    public MPInstancePara(MPInstance instance, int SeqNo) {
        super(0);
        setPInstanceId(instance.getPInstanceId());
        setSeqNo(SeqNo);
    } //	MPInstance_Para

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MPInstancePara(Row row) {
        super(row);
    } //	MPInstance_Para

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        StringBuilder sb =
                new StringBuilder("MPInstancePara[").append(getId()).append("-").append(getParameterName());
        if (getProcessString() != null) {
            sb.append("(s)=").append(getProcessString());
            if (getProcessStringTo() != null) sb.append(" - ").append(getProcessStringTo());
        }
        BigDecimal bd = getValue("P_Number");
        if (bd != null) {
            sb.append("(n)=").append(bd);
            BigDecimal bd2 = getValue("P_Number_To");
            if (bd2 != null) sb.append(" - ").append(bd2);
        }
        if (getProcessDate() != null) {
            sb.append("(d)=").append(getProcessDate());
            if (getProcessDateTo() != null) sb.append(" - ").append(getProcessDateTo());
        }
        sb.append("]");
        return sb.toString();
    } //	toString

} //	MPInstance_Para
