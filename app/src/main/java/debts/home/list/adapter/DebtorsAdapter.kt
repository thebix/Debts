package debts.home.list.adapter

import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import debts.common.android.adapters.CommonDiffUtilCallback
import debts.common.android.adapters.DelegatedAdapter
import debts.common.android.adapters.ItemRenderer
import debts.common.android.adapters.TypedAdapterDelegate
import debts.common.android.adapters.ViewHolderRenderer
import debts.common.android.extensions.*
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.thebix.debts.R

class DebtorsAdapter(
    private val itemClickCallback: ItemClickCallback
) : DelegatedAdapter() {

    companion object {
        const val TYPE_DEBTOR = 1
        const val TYPE_TITLE = 2
    }

    override var items: List<DebtorsItemViewModel> = emptyList()

    init {
        addDelegate(TYPE_DEBTOR, TypedAdapterDelegate { parent ->
            val layout = DebtorItemLayout(parent.context, itemClickCallback = itemClickCallback)
            ViewHolderRenderer(layout)
        })
        addDelegate(TYPE_TITLE, TypedAdapterDelegate { parent ->
            val layout = object : TextView(parent.context), ItemRenderer<DebtorsItemViewModel.TitleItem> {
                init {
                    doInRuntime {
                        applyLayoutParams()
                        setPaddingTopResCompat(R.dimen.padding_8dp)
                        setPaddingBottomResCompat(R.dimen.padding_8dp)
                        setPaddingStartResCompat(R.dimen.padding_16dp)
                        setBackgroundColor(context.getColorCompat(R.color.colorAccent))
                        setTextColor(context.getColorCompat(R.color.debts_white))
                        isAllCaps = true
                    }
                }

                override fun render(data: DebtorsItemViewModel.TitleItem) {
                    this.text = resources.getString(data.titleId)
                }
            }
            ViewHolderRenderer(layout)
        })
    }

    override fun getItemViewType(position: Int) = when (items[position]) {
        is DebtorsItemViewModel.DebtorItemViewModel -> TYPE_DEBTOR
        is DebtorsItemViewModel.TitleItem -> TYPE_TITLE
    }

    fun setItems(newItems: List<DebtorsItemViewModel>): Completable {
        return Single.fromCallable<DiffUtil.DiffResult> {
            DiffUtil.calculateDiff(CommonDiffUtilCallback(items, newItems))
        }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { result ->
                this.items = newItems
                result.dispatchUpdatesTo(this)
            }
            .ignoreElement()
    }

    fun replaceAllItems(newItems: List<DebtorsItemViewModel>) {
        this.items = newItems
        notifyDataSetChanged()
    }

    interface ItemClickCallback {

        fun onItemClick(debtorId: Long)
        fun onDebtorRemove(debtorId: Long)
        fun onDebtorShare(debtorId: Long)
    }
}
