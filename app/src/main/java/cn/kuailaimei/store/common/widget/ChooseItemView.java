package cn.kuailaimei.store.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.kuailaimei.store.R;
import cn.kuailaimei.store.api.entity.NormalItem;

/**
 * Created by ymh on 2016/7/13 0013.
 */
public class ChooseItemView extends ViewGroup {

    private static final String TAG = ChooseItemView.class.getSimpleName();
    private static final int TYPE_TEXT_NORMAL = 1;
    //    private final int DEFAULT_RIGHT_IMAGE = R.mipmap.icon_doublearrow;
    private final boolean mIsSingleSelect;

    private List<NormalItem> tags;

    private LayoutInflater mInflater;
    private OnTagClickListener onTagClickListener;
    Drawable drawableLeftSel = getResources().getDrawable(R.mipmap.sl_service_item_checked);
    Drawable drawableLeftNor = getResources().getDrawable(R.mipmap.sl_service_item_normal);

    private int sizeWidth;
    private int sizeHeight;

    private float mTagSize;
    private int mTagColor;
    private int mBackground;
    private int mViewBorder;
    private int mTagBorderHor;
    private int mTagBorderVer;

    private int mTagResId;
    //    private int mRightImageResId;
    private boolean mSingleLine;
    private boolean mShowRightImage;
    private boolean mShowEndText;
    private boolean mCanTagClick;
    private String endTextString;

    private int imageWidth;
    private int imageHeight;
    private ImageView imageView = null;

    private int endTextWidth = 0;
    private int endTextHeight = 0;
    private TextView endText = null;
    private int mTextPadding;
    private int mRowNum;
    private boolean isStayRight;

    private int DEFAULT_TEXT_COLOR;
    private static final int DEFAULT_TEXT_SIZE = 28;
    private static final int DEFAULT_TEXT_BACKGROUND = R.drawable.white;
    private static final int DEFAULT_VIEW_BORDER = 16;
    private static final int DEFAULT_TEXT_BORDER_HORIZONTAL = 0;
    private static final int DEFAULT_TEXT_BORDER_VERTICAL = 20;
    private static final int DEFAULT_TEXT_PADDING = 6;
    private static final int DEFAULT_ROW_NUM = 10086;

    private static final int DEFAULT_TAG_RESID = R.layout.item_tag_style;
    private static final boolean DEFAULT_SINGLE_LINE = false;
    private static final boolean DEFAULT_SHOW_RIGHT_IMAGE = true;
    private static final boolean DEFAULT_SHOW_END_TEXT = true;
    private static final boolean DEFAULT_CAN_TAG_CLICK = false;
    private static final boolean DEFAULT_STAY_RIGHT = false;
    private final boolean DEFAULT_SINGLE_SELECT = false;
    private final boolean DEFAULT_BACKGROUND_STATUS = false;
    protected SparseArray<TextView> chooseList = new SparseArray<>();
    private TextView mChoosed;

    public ChooseItemView(Context context) {
        this(context, null);
    }

    public ChooseItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChooseItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mInflater = LayoutInflater.from(context);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TagCloudView,
                defStyleAttr,
                defStyleAttr
        );
        DEFAULT_TEXT_COLOR = context.getResources().getColor(R.color.textColorPrimary);
        mTagSize = a.getDimensionPixelSize(R.styleable.TagCloudView_tcvTextSize, DEFAULT_TEXT_SIZE);
        mTagColor = a.getColor(R.styleable.TagCloudView_tcvTextColor, DEFAULT_TEXT_COLOR);
        mBackground = a.getResourceId(R.styleable.TagCloudView_tcvBackground, DEFAULT_TEXT_BACKGROUND);
        mViewBorder = a.getDimensionPixelSize(R.styleable.TagCloudView_tcvBorder, DEFAULT_VIEW_BORDER);
        mTagBorderHor = a.getDimensionPixelSize(
                R.styleable.TagCloudView_tcvItemBorderHorizontal, DEFAULT_TEXT_BORDER_HORIZONTAL);
        mTagBorderVer = a.getDimensionPixelSize(
                R.styleable.TagCloudView_tcvItemBorderVertical, DEFAULT_TEXT_BORDER_VERTICAL);
        mCanTagClick = a.getBoolean(R.styleable.TagCloudView_tcvCanTagClick, DEFAULT_CAN_TAG_CLICK);
        mIsSingleSelect = a.getBoolean(R.styleable.TagCloudView_tcvIsSingleSelect, DEFAULT_SINGLE_SELECT);

        mSingleLine = a.getBoolean(R.styleable.TagCloudView_tcvSingleLine, DEFAULT_SINGLE_LINE);
        mShowRightImage = a.getBoolean(R.styleable.TagCloudView_tcvShowRightImg, DEFAULT_SHOW_RIGHT_IMAGE);
        mShowEndText = a.getBoolean(R.styleable.TagCloudView_tcvShowEndText, DEFAULT_SHOW_END_TEXT);
        endTextString = a.getString(R.styleable.TagCloudView_tcvEndText);
        mTextPadding = a.getDimensionPixelSize(R.styleable.TagCloudView_tcvTagTextPadding, DEFAULT_TEXT_PADDING);
        mRowNum = a.getInteger(R.styleable.TagCloudView_tcvRowNum, DEFAULT_ROW_NUM);
        isStayRight = a.getBoolean(R.styleable.TagCloudView_tcvIsStayRight, DEFAULT_STAY_RIGHT);

        mTagResId = a.getResourceId(R.styleable.TagCloudView_tcvTagResId, DEFAULT_TAG_RESID);

        a.recycle();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return (!mCanTagClick && mSingleLine) || super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    /**
     * 计算 ChildView 宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 计算 ViewGroup 上级容器为其推荐的宽高
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        //计算 childView 宽高
        measureChildren(widthMeasureSpec, heightMeasureSpec);

//        initSingleLineView(widthMeasureSpec, heightMeasureSpec);

        int totalWidth = 0;
        int totalHeight = mTagBorderVer;

        if (mSingleLine) {
            totalHeight = getSingleTotalHeight(totalWidth, totalHeight);
        } else {
            totalHeight = getMultiTotalHeight(totalWidth, totalHeight);
        }

        /**
         * 高度根据设置改变
         * 如果为 MATCH_PARENT 则充满父窗体，否则根据内容自定义高度
         */
        setMeasuredDimension(
                sizeWidth,
                (heightMode == MeasureSpec.EXACTLY ? sizeHeight : totalHeight));

    }


    /**
     * 为 singleLine 模式布局，并计算视图高度
     *
     * @param totalWidth
     * @param totalHeight
     * @return
     */
    private int getSingleTotalHeight(int totalWidth, int totalHeight) {
        int childWidth;
        int childHeight;

        totalWidth += mViewBorder;

        int textTotalWidth = getTextTotalWidth();
        if (textTotalWidth < sizeWidth - imageWidth) {
            endText = null;
            endTextWidth = 0;
        }

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();


            if (i == 0) {
                totalWidth += childWidth;
                totalHeight = childHeight + mViewBorder;
            } else {
                totalWidth += childWidth + mTagBorderHor;
            }
            int tag = -1;
            Object obj = child.getTag();
            if (obj instanceof Integer) {
                tag = (Integer) obj;
            }
            if (child.getTag() != null && tag == TYPE_TEXT_NORMAL) {
                if (totalWidth + mTagBorderHor + mViewBorder + mViewBorder + endTextWidth + imageWidth < sizeWidth) {
                    child.layout(
                            totalWidth - childWidth + mTagBorderVer,
                            totalHeight - childHeight,
                            totalWidth + mTagBorderVer,
                            totalHeight);
                } else {
                    totalWidth -= childWidth + mViewBorder;
                    break;
                }
            }
        }

        if (endText != null) {
            endText.layout(
                    totalWidth + mViewBorder + mTagBorderVer,
                    totalHeight - endTextHeight,
                    totalWidth + mViewBorder + mTagBorderVer + endTextWidth,
                    totalHeight);
        }

        totalHeight += mViewBorder;

        if (imageView != null) {
            imageView.layout(
                    sizeWidth - imageWidth - mViewBorder,
                    (totalHeight - imageHeight) / 2,
                    sizeWidth - mViewBorder,
                    (totalHeight - imageHeight) / 2 + imageHeight);
        }

        return totalHeight;
    }

    /**
     * 为 multiLine 模式布局，并计算视图高度
     *
     * @param totalWidth
     * @param totalHeight
     * @return
     */
    private int getMultiTotalHeight(int totalWidth, int totalHeight) {
        int childWidth;
        int childHeight;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();
            //            if(childWidth+mViewBorder
            //            		+getPaddingLeft()+getPaddingRight()>getWidth()){
            //            	childWidth = getWidth()-mViewBorder*4;
            //            }
            totalWidth += childWidth + mViewBorder;

            if (i == 0) {
                totalHeight = childHeight;
            }

            if ((totalHeight - childHeight) >= (childHeight + mTagBorderVer) * (mRowNum - 1)) {
                return totalHeight;
            }
            // + marginLeft 保证最右侧与 ViewGroup 右边距有边界
            if (totalWidth + mTagBorderHor + mViewBorder > sizeWidth) {

                totalWidth = mViewBorder;
                totalHeight += childHeight + mTagBorderVer;
                if (isStayRight) {
                    child.layout(
                            sizeWidth - totalWidth - childWidth,
                            totalHeight - childHeight,
                            sizeWidth - totalWidth,
                            totalHeight);
                } else {
                    child.layout(
                            totalWidth + mTagBorderHor,
                            totalHeight - childHeight,
                            totalWidth + childWidth + mTagBorderHor,
                            totalHeight);
                }
                totalWidth += childWidth;

            } else {
                if (isStayRight) {
                    child.layout(
                            sizeWidth - totalWidth,
                            totalHeight - childHeight,
                            sizeWidth - totalWidth + childWidth,
                            totalHeight);
                } else {
                    child.layout(
                            totalWidth - childWidth + mTagBorderHor,
                            totalHeight - childHeight,
                            totalWidth + mTagBorderHor,
                            totalHeight);
                }

            }
        }
        return totalHeight;
    }

    private int getTextTotalWidth() {
        if (getChildCount() == 0) {
            return 0;
        }
        int totalChildWidth = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Object obj = child.getTag();
            int tag = -1;
            if (obj instanceof Integer) {
                tag = (Integer) obj;
            }
            if (child.getTag() != null && tag == TYPE_TEXT_NORMAL) {
                totalChildWidth += child.getMeasuredWidth() + mViewBorder;
            }
        }
        return totalChildWidth + mTagBorderHor * 2;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return super.generateLayoutParams(attrs);
    }

    public void setTags(List<NormalItem> tagList) {
        if (tags == tagList) return;
        if (tags != null && tags.size() > 0) {
            tags.clear();
            removeAllViews();
        }
        this.tags = tagList;
        if (tags != null && tags.size() > 0) {
            for (int i = 0; i < tags.size(); i++) {
                final NormalItem item = tags.get(i);
                TextView tagView = (TextView) mInflater.inflate(mTagResId, null);
                tagView.setBackgroundResource(mBackground);
                tagView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTagSize);
                tagView.setTextColor(mTagColor);
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                tagView.setPadding(mTextPadding == DEFAULT_TEXT_PADDING ? mTextPadding * 3 : mTextPadding, mTextPadding, mTextPadding == DEFAULT_TEXT_PADDING ? mTextPadding * 3 : mTextPadding, mTextPadding);
                //                tagView.setMaxWidth(getWidth()-mViewBorder*4);
                tagView.setLayoutParams(layoutParams);
                tagView.setText(item.getName());
                tagView.setTag(TYPE_TEXT_NORMAL);
                Drawable drawableLeft;
                final int finalPosition = i;
                if(item.isSelected()){
                    drawableLeft = drawableLeftSel;
                    chooseList.put(finalPosition, tagView);
                }else{
                    drawableLeft = drawableLeftNor;
                }
                if(drawableLeft!=null)drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
                tagView.setCompoundDrawables(drawableLeft, null, null, null);
                tagView.setCompoundDrawablePadding((int) mTagSize);
                tagView.setBackgroundResource(mBackground);
                tagView.setTextColor(mTagColor);
                if (mCanTagClick) {
                    tagView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mIsSingleSelect)
                                markSingleChooseView((TextView) v, item,finalPosition);
                            else
                                markChooseView((TextView) v, item,finalPosition);
                        }
                    });
                }
                addView(tagView);
            }
        }
        postInvalidate();
    }

    public void markChooseView(TextView current,NormalItem item,int position) {
        TextView choosed = chooseList.get(position);
        if (current != choosed) {
            if(drawableLeftSel!=null)drawableLeftSel.setBounds(0, 0, drawableLeftSel.getMinimumWidth(), drawableLeftSel.getMinimumHeight());
            current.setCompoundDrawables(drawableLeftSel, null, null, null);
            current.setCompoundDrawablePadding((int) mTagSize/2);
            chooseList.put(position, current);
            if (onTagClickListener != null)
                onTagClickListener.onTagClick(item, true);
        } else {
            if(drawableLeftNor!=null)drawableLeftNor.setBounds(0, 0, drawableLeftNor.getMinimumWidth(), drawableLeftNor.getMinimumHeight());
            current.setCompoundDrawables(drawableLeftNor, null, null, null);
            current.setCompoundDrawablePadding((int) mTagSize/2);
            chooseList.remove(position);
            if (onTagClickListener != null)
                onTagClickListener.onTagClick(item, false);
        }


    }

    public void markSingleChooseView(TextView current, NormalItem item,int position) {
        TextView choosed = chooseList.get(position);
        if (mChoosed != null && mChoosed != choosed) {
            current.setCompoundDrawables(drawableLeftNor, null, null, null);
            current.setCompoundDrawablePadding((int) mTagSize);
        }
        if (mChoosed != null && mChoosed != choosed || mChoosed == null) {
            if (onTagClickListener != null)
                onTagClickListener.onTagClick(item, true);
        }

        mChoosed = current;
        current.setCompoundDrawables(drawableLeftSel, null, null, null);
        current.setCompoundDrawablePadding((int) mTagSize);
        chooseList.put(position, current);
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        this.onTagClickListener = onTagClickListener;
    }

    public interface OnTagClickListener {
        /**
         * @param item 选择的标签
         * @param isChoose       是选择该标签还是取消
         */
        void onTagClick(NormalItem item, boolean isChoose);
    }
}
