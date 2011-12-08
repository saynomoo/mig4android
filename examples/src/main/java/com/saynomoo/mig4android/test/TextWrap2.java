package com.saynomoo.mig4android.test;

import com.saynomoo.mig4android.MigActivity;
import com.saynomoo.mig4android.MigLayout;

public class TextWrap2 extends MigActivity {
    @Override
    public MigLayout createLayout() {
        return new TextWrap2View();
    }
    class TextWrap2View extends MigLayout {
        public TextWrap2View() {
            super(TextWrap2.this);
            addLabel(this, "name asfd aasdf a sdf ads f dsaf af sdf as df asdf a sdf as fa sf as dfasdf a sdf ads f dsaf af sdf as df asdf a sdf as fa sf as df", "");
        }
    }
}
