package com.swayni.sportsbettingapp.core.base_ui

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.swayni.sportsbettingapp.core.annotation.BindingOnly

abstract class BaseActivity<B : ViewDataBinding, VM: ViewModel>(
    @LayoutRes private val layoutId: Int,
    private val viewModelClass : Class<VM>
) : AppCompatActivity() {

    /** This interface is generated during compilation to contain getters for all used instance `BindingAdapters`. */
    private var bindingComponent: DataBindingComponent? = DataBindingUtil.getDefaultComponent()

    protected val viewModel : VM by lazy {
        ViewModelProvider(this)[viewModelClass]
    }
    /**
     * A data-binding property will be initialized before being called [onCreate].
     * And inflates using the [layoutId] as a content view for activities.
     */
    @BindingOnly
    protected val binding: B by lazy(LazyThreadSafetyMode.NONE) {
        DataBindingUtil.setContentView(this, layoutId, bindingComponent)
    }
    /**
     * An executable inline binding function that receives a binding receiver in lambda.
     *
     * @param block A lambda block will be executed with the binding receiver.
     * @return T A generic class that extends [ViewDataBinding] and generated by DataBinding on compile time.
     */
    @BindingOnly
    protected inline fun binding(block: B.() -> Unit): B {
        return binding.apply(block)
    }

    /**
     * Ensures the [binding] property should be executed before being called [onCreate].
     */
    init {
        addOnContextAvailableListener {
            binding.notifyChange()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        observeData()
    }


    abstract fun init()
    open fun observeData(){}
    /**
     * Removes binding listeners to expression variables and destroys the [binding] backing property for preventing
     * leaking the [ViewDataBinding] that references the Context.
     */
    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}