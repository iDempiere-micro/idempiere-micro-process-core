package org.compiere.process;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Process Instance Parameter Model
 *
 * @author Jorg Janke
 * @version $Id: MPInstancePara.java,v 1.3 2006/07/30 00:58:05 jjanke Exp $
 */
public class MPInstancePara extends X_AD_PInstance_Para {

  /** */
  private static final long serialVersionUID = -8407658637240252680L;

  /**
   * Persistence Constructor
   *
   * @param ctx context
   * @param ignored ignored
   * @param trxName transaction
   */
  public MPInstancePara(Properties ctx, int ignored) {
    super(ctx, 0);
    if (ignored != 0) throw new IllegalArgumentException("Multi-Key");
  } //	MPInstance_Para

  /**
   * Parent Constructor
   *
   * @param ctx
   * @param AD_PInstance_ID id
   * @param SeqNo sequence
   */
  public MPInstancePara(Properties ctx, int AD_PInstance_ID, int SeqNo) {
    super(ctx, 0);
    setAD_PInstance_ID(AD_PInstance_ID);
    setSeqNo(SeqNo);
  } //	MPInstance_Para

  /**
   * Parent Constructor
   *
   * @param instance instance
   * @param SeqNo sequence
   */
  public MPInstancePara(MPInstance instance, int SeqNo) {
    super(instance.getCtx(), 0);
    setAD_PInstance_ID(instance.getAD_PInstance_ID());
    setSeqNo(SeqNo);
  } //	MPInstance_Para

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MPInstancePara(Properties ctx, ResultSet rs) {
    super(ctx, rs);
  } //	MPInstance_Para

  /**
   * String Representation
   *
   * @return info
   */
  public String toString() {
    StringBuffer sb =
        new StringBuffer("MPInstancePara[").append(getId()).append("-").append(getParameterName());
    if (getP_String() != null) {
      sb.append("(s)=").append(getP_String());
      if (getP_String_To() != null) sb.append(" - ").append(getP_String_To());
    }
    BigDecimal bd = (BigDecimal) get_Value("P_Number");
    if (bd != null) {
      sb.append("(n)=").append(bd);
      BigDecimal bd2 = (BigDecimal) get_Value("P_Number_To");
      if (bd2 != null) sb.append(" - ").append(bd2);
    }
    if (getP_Date() != null) {
      sb.append("(d)=").append(getP_Date());
      if (getP_Date_To() != null) sb.append(" - ").append(getP_Date_To());
    }
    sb.append("]");
    return sb.toString();
  } //	toString

    /**
   * Set String Parameter
   *
   * @param parameterName name
   * @param stringParameter value
   */
  public void setParameter(String parameterName, String stringParameter) {
    setParameterName(parameterName);
    setP_String(stringParameter);
  } //	setParameter

  /**
   * Set Number Parameter
   *
   * @param parameterName name
   * @param bdParameter value
   */
  public void setParameter(String parameterName, BigDecimal bdParameter) {
    setParameterName(parameterName);
    setP_Number(bdParameter);
  } //	setParameter

  /**
   * Set Number Parameter
   *
   * @param parameterName name
   * @param iParameter value
   */
  public void setParameter(String parameterName, int iParameter) {
    setParameterName(parameterName);
    setP_Number(new BigDecimal(iParameter));
  } //	setParameter

  /**
   * Set Date Parameter
   *
   * @param parameterName name
   * @param tsParameter value
   */
  public void setParameter(String parameterName, Timestamp tsParameter) {
    setParameterName(parameterName);
    setP_Date(tsParameter);
  } //	setParameter

  /**
   * Set Boolean Parameter
   *
   * @param parameterName name
   * @param boolParameter value
   */
  public void setParameter(String parameterName, boolean boolParameter) {
    setParameterName(parameterName);
    setP_String(boolParameter ? "Y" : "N");
  } //	setParameter

} //	MPInstance_Para
