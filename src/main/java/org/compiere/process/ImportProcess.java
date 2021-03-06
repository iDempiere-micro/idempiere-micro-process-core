package org.compiere.process;

/**
 * All processes that are importing data should implement this interface.
 *
 * @author Teo Sarca, www.arhipac.ro
 *     <li>FR [ 2788276 ] Data Import Validator
 *         https://sourceforge.net/tracker/?func=detail&aid=2788276&group_id=176962&atid=879335
 */
public interface ImportProcess {
    /** @return The Name of Import Table (e.g. I_BPartner) */
    String getImportTableName();

    /** @return SQL WHERE clause to filter records that are candidates for import */
    String getWhereClause();

}
