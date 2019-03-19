package debts.home.details.adapter

import debts.common.android.adapters.DelegatedAdapter
import debts.common.android.adapters.TypedAdapterDelegate
import debts.common.android.adapters.ViewHolderRenderer

class DebtsAdapter : DelegatedAdapter() {

    companion object {
        const val TYPE_DEBT = 1
    }

    override var items: List<DebtsItemViewModel> = emptyList()

    init {
        addDelegate(TYPE_DEBT, TypedAdapterDelegate { parent ->
            val layout = DebtItemLayout(parent.context)
            ViewHolderRenderer(layout)
        })
    }

    override fun getItemViewType(position: Int) = when (items[0]) {
        is DebtsItemViewModel.DebtItemViewModel -> TYPE_DEBT
    }

    fun replaceAllItems(newItems: List<DebtsItemViewModel>) {
        this.items = newItems
        notifyDataSetChanged()
    }
}
