package org.compiere.process;

import org.compiere.model.IProcessInfoParameter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Process Parameter
 *
 * @author Jorg Janke
 * @author Teo Sarca, www.arhipac.ro
 * <li>FR [ 2430845 ] Add ProcessInfoParameter.getParameterAsBoolean method
 * @version $Id: ProcessInfoParameter.java,v 1.2 2006/07/30 00:54:44 jjanke Exp $
 */
public class ProcessInfoParameter implements Serializable, IProcessInfoParameter {
    /**
     *
     */
    private static final long serialVersionUID = -8571973325856537109L;
    private String m_ParameterName;
    private Object m_Parameter;
    private Object m_Parameter_To;
    private String m_Info = "";
    private String m_Info_To = "";
    /**
     * Construct Parameter
     *
     * @param parameterName parameter name
     * @param parameter     parameter
     * @param parameter_To  to parameter
     * @param info          info
     * @param info_To       to info
     */
    public ProcessInfoParameter(
            String parameterName, Object parameter, Object parameter_To, String info, String info_To) {
        setParameterName(parameterName);
        setParameter(parameter);
        setParameter_To(parameter_To);
        setInfo(info);
        setInfo_To(info_To);
    } //  ProcessInfoParameter

    /**
     * String Representation
     *
     * @return info
     */
    public String toString() {
        //	From .. To
        if (m_Parameter_To != null || m_Info_To.length() > 0)
            return "ProcessInfoParameter["
                    + m_ParameterName
                    + "="
                    + m_Parameter
                    + (m_Parameter == null ? "" : "{" + m_Parameter.getClass().getName() + "}")
                    + " ("
                    + m_Info
                    + ") - "
                    + m_Parameter_To
                    + (m_Parameter_To == null ? "" : "{" + m_Parameter_To.getClass().getName() + "}")
                    + " ("
                    + m_Info_To
                    + ")";
        //	Value
        return "ProcessInfoParameter["
                + m_ParameterName
                + "="
                + m_Parameter
                + (m_Parameter == null ? "" : "{" + m_Parameter.getClass().getName() + "}")
                + " ("
                + m_Info
                + ")";
    } //	toString

    /**
     * Method getParameter
     *
     * @return Object
     */
    public Object getParameter() {
        return m_Parameter;
    }

    /**
     * Method setParameter
     *
     * @param Parameter Object
     */
    public void setParameter(Object Parameter) {
        m_Parameter = Parameter;
    }

    /**
     * Method getParameter as Int
     *
     * @return Object
     */
    public int getParameterAsInt() {
        if (m_Parameter == null) return 0;
        if (m_Parameter instanceof Number) return ((Number) m_Parameter).intValue();
        try {
            String m_ParameterS = m_Parameter.toString();
            if (m_ParameterS.equals("null")) return 0;
            BigDecimal bd = new BigDecimal(m_ParameterS);
            return bd.intValue();
        } catch (java.lang.NumberFormatException ex) {
            throw new IllegalArgumentException("Failed for'" + m_Parameter + "'", ex);
        }
    } //	getParameterAsInt

    /**
     * Method getParameter as Boolean
     *
     * @return boolean value
     */
    public boolean getParameterAsBoolean() {
        if (m_Parameter == null) return false;
        if (m_Parameter instanceof Boolean) return ((Boolean) m_Parameter).booleanValue();
        return "Y".equals(m_Parameter);
    }

    /**
     * Method getParameter_To
     *
     * @return Object
     */
    public Object getParameter_To() {
        return m_Parameter_To;
    }

    /**
     * Method setParameter_To
     *
     * @param Parameter_To Object
     */
    public void setParameter_To(Object Parameter_To) {
        m_Parameter_To = Parameter_To;
    }

    /**
     * Method getParameter as Timestamp
     *
     * @return Object
     */
    public Timestamp getParameterAsTimestamp() {
        if (m_Parameter == null) return null;
        if (m_Parameter instanceof Timestamp) return (Timestamp) m_Parameter;
        return null;
    } //	getParameterAsTimestamp

    /**
     * Method getParameter as String
     *
     * @return Object
     */
    public String getParameterAsString() {
        if (m_Parameter == null) return null;
        return m_Parameter.toString();
    } //	getParameterAsString

    /**
     * Method getParameter as BigDecimal
     *
     * @return Object
     */
    public BigDecimal getParameterAsBigDecimal() {
        if (m_Parameter == null) return null;
        if (m_Parameter instanceof BigDecimal) return (BigDecimal) m_Parameter;
        return new BigDecimal(m_Parameter.toString());
    } //	getParameterAsBigDecimal

    /**
     * Method getParameterName
     *
     * @return String
     */
    public String getParameterName() {
        return m_ParameterName;
    }

    /**
     * Method setParameterName
     *
     * @param ParameterName String
     */
    public void setParameterName(String ParameterName) {
        m_ParameterName = ParameterName;
    }

    /**
     * Method setInfo
     *
     * @param Info String
     */
    public void setInfo(String Info) {
        if (Info == null) m_Info = "";
        else m_Info = Info;
    }

    /**
     * Method setInfo_To
     *
     * @param Info_To String
     */
    public void setInfo_To(String Info_To) {
        if (Info_To == null) m_Info_To = "";
        else m_Info_To = Info_To;
    }
} //  ProcessInfoParameter
