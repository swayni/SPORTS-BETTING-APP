package com.swayni.sportsbettingapp.core.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.swayni.sportsbettingapp.R

class BettingButton : ConstraintLayout {

    private var titleText: String? = null
    private var betText: String ?= null
    private var isChecked : Boolean = false
    private var isEnabled : Boolean = true

    private var listener : (() -> Unit)? = null

    private lateinit var cvBackground : CardView
    private lateinit var vBackground : View
    private lateinit var tvTitle : TextView
    private lateinit var tvBetText : TextView

    constructor(context : Context) : super(context){
        inflate()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        inflate()
        initialize(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr : Int) : super(context, attrs, defStyleAttr){
        inflate()
        initialize(context, attrs)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredHeight, measuredHeight)
    }

    private fun inflate() {
        val view = LayoutInflater.from(context).inflate(R.layout.component_betting_button, this, true)
        cvBackground = view.findViewById(R.id.cvBackground)
        vBackground = view.findViewById(R.id.title_background)
        tvTitle = view.findViewById(R.id.title)
        tvBetText = view.findViewById(R.id.bet_text)
    }

    private fun initialize(context: Context, attrs: AttributeSet?) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.BettingButton,
            0, 0
        )
        try {
            if (typedArray.hasValue(R.styleable.BettingButton_titleValue)) {
                titleText = typedArray.getString(R.styleable.BettingButton_titleValue)
            }
            if (typedArray.hasValue(R.styleable.BettingButton_betText)) {
                betText = typedArray.getString(R.styleable.BettingButton_betText)
            }
            if (typedArray.hasValue(R.styleable.BettingButton_isChecked)){
                isChecked = typedArray.getBoolean(R.styleable.BettingButton_isChecked, false)
            }
            if (typedArray.hasValue(R.styleable.BettingButton_isEnabled)){
                isEnabled = typedArray.getBoolean(R.styleable.BettingButton_isEnabled, true)
            }
        } finally {
            typedArray.recycle()
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        init()
    }

    private fun init(){
        tvTitle.text = titleText
        tvBetText.text = betText
        if (isEnabled) {
            setOnClickListener {

                isChecked = !isChecked
                checked(isChecked)
                listener?.invoke()
            }
        }
    }

    private fun checked(isChecked : Boolean){
        if (isEnabled) {
            if (isChecked) {
                vBackground.setBackgroundColor(
                    resources.getColor(
                        R.color.green,
                        resources.newTheme()
                    )
                )
                tvTitle.setTextColor(resources.getColor(R.color.black, resources.newTheme()))
            } else {
                vBackground.setBackgroundColor(
                    resources.getColor(
                        R.color.midnight_green,
                        resources.newTheme()
                    )
                )
                tvTitle.setTextColor(resources.getColor(R.color.white, resources.newTheme()))
            }
        }else{
            vBackground.setBackgroundColor(
                resources.getColor(
                    R.color.midnight_green_alpha,
                    resources.newTheme()
                )
            )
            tvTitle.setTextColor(resources.getColor(R.color.white, resources.newTheme()))
        }
    }

    fun setBettingButton(
        betText: String,
        isChecked: Boolean,
        isEnabled : Boolean,
        listener : (() -> Unit)? = null){

        this.betText = betText
        this.isChecked = isChecked
        this.listener = listener
        this.isEnabled = isEnabled

        tvBetText.text = betText
        checked(isChecked)

        init()
    }
}