package com.vit.updateplugin.callback;

import com.vit.updateplugin.parse.UpdateBean;

/**
 * <p> <p/>
 *
 * @author kewz
 */

public interface OnCheckUpdateListener {

    /**
     * 更新检查开始
     */
    void onCheckStart();

    /**
     * 需要进行版本更新
     *
     * @param mUpdate
     */
    void hasUpdate(UpdateBean mUpdate);

    /**
     * 不需要进行版本更新
     */
    void noUpdate();

    /**
     * 异常
     *
     * @param t
     */
    void onCheckError(Throwable t);

}
