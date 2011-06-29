package com.saynomoo.mig4android;

public class QuickStart extends MigActivity{
    @Override
    public MigLayout createLayout() {
        return new QuicStartView();
    }

    class QuicStartView extends MigLayout {
        public QuicStartView() {
            super(QuickStart.this);
            setLayoutConstraints("wrap");
            setColumnConstraints("[right][fill,sizegroup]unrel[right][fill,sizegroup]");
            addSeparator(this, "General");
            addLabel(this, "Company", "gap indent");
            addTextField(this, "span,growx");
            addLabel(this, "Contact", "gap indent");
            addTextField(this, "span,growx");

            addSeparator(this, "Propeller");
            addLabel(this, "PTI/kW", "gap indent");
            addTextField(this, "wmin 130");
            addLabel(this, "Power/kW", "gap indent");
            addTextField(this, "wmin 130");
            addLabel(this, "R/mm", "gap indent");
            addTextField(this, "wmin 130");
            addLabel(this, "D/mm", "gap indent");
            addTextField(this, "wmin 130");
        }
    }

}
