package org.compiere.process

import org.compiere.model.DocProps
import org.compiere.util.SystemIDs
import org.idempiere.common.exceptions.AdempiereException
import org.idempiere.icommon.model.BasePersistentObject

/**
 * Posting Document actions.
 */
interface DocAction<T> : BasePersistentObject, DocProps {

    /*************************************************************************
     * Process document
     * @param action document action
     * @return true if performed
     * @throws Exception
     */
    @Throws(Exception::class)
    fun processIt(action: String): Boolean

    /**
     * Unlock Document.
     * @return true if success
     */
    fun unlockIt(): Boolean

    /**
     * Invalidate Document
     * @return true if success
     */
    fun invalidateIt(): Boolean

    /**
     * Prepare Document
     * @return new status (In Progress or Invalid)
     */
    fun prepareIt(): String

    /**
     * Approve Document
     * @return true if success
     */
    fun approveIt(): Boolean

    /**
     * Reject Approval
     * @return true if success
     */
    fun rejectIt(): Boolean

    /**
     * Complete Document
     * @return new status (Complete, In Progress, Invalid, Waiting ..)
     */
    fun completeIt(): CompleteActionResult<T>

    /**
     * Void Document
     * @return true if success
     */
    fun voidIt(): Boolean

    /**
     * Close Document
     * @return true if success
     */
    fun closeIt(): Boolean

    /**
     * Reverse Correction
     * @return true if success
     */
    fun reverseCorrectIt(): Boolean

    /**
     * Reverse Accrual
     * @return true if success
     */
    fun reverseAccrualIt(): Boolean

    /**
     * Re-activate
     * @return true if success
     */
    fun reActivateIt(): Boolean

    /**
     * Save throwing exception
     * @throws AdempiereException
     * @see .save
     */
    @Throws(AdempiereException::class)
    fun saveEx()

    companion object {
        /** Complete = CO  */
        val ACTION_Complete = "CO"
        /** Wait Complete = WC  */
        val ACTION_WaitComplete = "WC"
        /** Approve = AP  */
        val ACTION_Approve = "AP"
        /** Reject = RJ  */
        val ACTION_Reject = "RJ"
        /** Post = PO  */
        val ACTION_Post = "PO"
        /** Void = VO  */
        val ACTION_Void = "VO"
        /** Close = CL  */
        val ACTION_Close = "CL"
        /** Reverse - Correct = RC  */
        val ACTION_Reverse_Correct = "RC"
        /** Reverse - Accrual = RA  */
        val ACTION_Reverse_Accrual = "RA"
        /** ReActivate = RE  */
        val ACTION_ReActivate = "RE"
        /** <None> = -- </None> */
        val ACTION_None = "--"
        /** Prepare = PR  */
        val ACTION_Prepare = "PR"
        /** Unlock = XL  */
        val ACTION_Unlock = "XL"
        /** Invalidate = IN  */
        val ACTION_Invalidate = "IN"
        /** ReOpen = OP  */
        val ACTION_ReOpen = "OP"

        /** Drafted = DR  */
        val STATUS_Drafted = "DR"
        /** Completed = CO  */
        val STATUS_Completed = "CO"
        /** Approved = AP  */
        val STATUS_Approved = "AP"
        /** Invalid = IN  */
        val STATUS_Invalid = "IN"
        /** Not Approved = NA  */
        val STATUS_NotApproved = "NA"
        /** Voided = VO  */
        val STATUS_Voided = "VO"
        /** Reversed = RE  */
        val STATUS_Reversed = "RE"
        /** Closed = CL  */
        val STATUS_Closed = "CL"
        /** Unknown = ??  */
        val STATUS_Unknown = "??"
        /** In Progress = IP  */
        val STATUS_InProgress = "IP"
        /** Waiting Payment = WP  */
        val STATUS_WaitingPayment = "WP"
        /** Waiting Confirmation = WC  */
        val STATUS_WaitingConfirmation = "WC"

        /** DocAction Ref_List values  */
        val ReferenceId = SystemIDs.REFERENCE_DOCUMENTACTION
    }
}