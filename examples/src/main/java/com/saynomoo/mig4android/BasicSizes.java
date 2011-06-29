package com.saynomoo.mig4android;

public class BasicSizes extends MigActivity{
    @Override
    public MigLayout createLayout() {
        return new BasicSizesView();
    }

    class BasicSizesView extends MigLayout {
        public BasicSizesView() {
            super(BasicSizes.this);
            setColumnConstraints("[]15[75px]25[min]25[]");
            setRowConstraints("[]15");
            addLabel(this, "75px", "skip");
            addLabel(this, "Min", "");
            addLabel(this, "Pref", "wrap");

            addLabel(this, "new TextField(15)", "");
            addTextField(this, "               ", "wmin 30");
            addTextField(this, "               ", "wmin 30");
            addTextField(this, "               ", "wmin 30");
        }
    }

}
