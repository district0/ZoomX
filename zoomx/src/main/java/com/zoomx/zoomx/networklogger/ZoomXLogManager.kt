package com.zoomx.zoomx.networklogger

import com.zoomx.zoomx.config.ZoomX
import com.zoomx.zoomx.model.RequestEntity
import com.zoomx.zoomx.ui.ZoomxUIOption
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by Ahmed Fathallah on 1/11/2018.
 */

class ZoomXLogManager {

    companion object {

        @JvmStatic
        fun log(builder: RequestEntity.Builder) {
            insertRequest(builder).subscribe()
        }

        @JvmStatic
        private fun insertRequest(builder: RequestEntity.Builder): Completable {
            return Completable
                    .fromAction { ZoomX.getRequestDao().insertRequest(builder.create()) }
                    .doOnError { Timber.e(it) }
                    .observeOn(AndroidSchedulers.mainThread())
                    .andThen {
                        if (ZoomX.getSettingsManager()?.zoomxUIOption == ZoomxUIOption.NOTIFICATION) {
                            ZoomX.getNotification()?.show(builder.create())
                        }
                    }
                    .subscribeOn(Schedulers.io())
        }

    }
}
