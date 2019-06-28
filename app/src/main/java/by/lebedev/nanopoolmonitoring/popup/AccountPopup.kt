package by.lebedev.nanopoolmonitoring.popup

import android.widget.LinearLayout
import android.provider.SyncStateContract.Helpers.update
import android.view.Gravity
import android.widget.TextView
import android.widget.PopupWindow
import android.os.Bundle
import android.app.Activity
import android.view.View
import android.widget.Button


class AccountPopup : Activity() {

    lateinit var popUp: PopupWindow
    lateinit var layout: LinearLayout
    lateinit var tv: TextView
    lateinit var params: LinearLayout.LayoutParams
    lateinit var mainLayout: LinearLayout
    lateinit var but: Button
    internal var click = true

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        popUp = PopupWindow(this)
        layout = LinearLayout(this)
        mainLayout = LinearLayout(this)
        tv = TextView(this)
        but = Button(this)
        but.setText("Click Me")
        but.setOnClickListener(object : View.OnClickListener {

           override fun onClick(v: View) {
                if (click) {
                    popUp.showAtLocation(layout, Gravity.BOTTOM, 10, 10)
                    popUp.update(50, 50, 300, 80)
                    click = false
                } else {
                    popUp.dismiss()
                    click = true
                }
            }

        })
        params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layout.orientation = LinearLayout.VERTICAL
        tv.text = "Hi this is a sample text for popup window"
        layout.addView(tv, params)
        popUp.contentView = layout
        // popUp.showAtLocation(layout, Gravity.BOTTOM, 10, 10);
        mainLayout.addView(but, params)
        setContentView(mainLayout)
    }
}