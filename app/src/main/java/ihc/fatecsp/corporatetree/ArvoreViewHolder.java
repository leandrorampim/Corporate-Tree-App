package ihc.fatecsp.corporatetree;

import android.view.View;
import android.widget.TextView;

public class ArvoreViewHolder {
    TextView mTextView;
    ArvoreViewHolder(View view) {
        mTextView = view.findViewById(R.id.textView);
    }

}
