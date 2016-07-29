package cn.kuailaimei.store.bossmanage.datasource;

import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;
import com.zitech.framework.data.network.response.ApiResponse;

import java.util.List;

import cn.kuailaimei.store.api.ApiFactory;
import cn.kuailaimei.store.api.entity.CommentItem;
import cn.kuailaimei.store.api.request.PageRequest;
import cn.kuailaimei.store.api.response.CommentListResponse;
import cn.kuailaimei.store.common.datasource.ListFetcher;
import cn.kuailaimei.store.common.datasource.PagedProxy;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by ymh on 2016/7/3 0003.
 */
public class CommentListDataSource implements IAsyncDataSource<List<CommentItem>> {
    private PagedProxy proxy = new PagedProxy(20);
    private PageRequest request;
    private ListFetcher<CommentItem> mListFetcher;

    public CommentListDataSource(PageRequest pageRequest, ListFetcher<CommentItem> listFetcher) {
        super();
        this.request = pageRequest;
        this.mListFetcher = listFetcher;
    }

    @Override
    public RequestHandle refresh(ResponseSender<List<CommentItem>> sender) throws Exception {
        return loadStores(sender, proxy.reset());
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<CommentItem>> sender) throws Exception {
        return loadStores(sender, proxy.toNextPage());
    }

    @Override
    public boolean hasMore() {
        return proxy.hasNextPage();
    }

    private RequestHandle loadStores(final ResponseSender<List<CommentItem>> sender, final int page) throws Exception {
        request.setCurrentPage(page);
        final Subscription subscribe = ApiFactory.getCommentList(request).subscribe(new Action1<ApiResponse<CommentListResponse>>() {
            @Override
            public void call(ApiResponse<CommentListResponse> response) {
                CommentListResponse data = response.getData();
                if (!proxy.isPageCountSet()) {
                    proxy.setDataCount(data.getPagination().getRows());
                }
                List<CommentItem> items = mListFetcher.fetch(response);
                if (items == null || items.size() == 0) {
                    proxy.setReachEnd(true);
                }
                sender.sendData(items);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                sender.sendError(new Exception(throwable));
            }
        });


        return new RequestHandle() {
            @Override
            public void cancel() {
                subscribe.unsubscribe();
            }

            @Override
            public boolean isRunning() {
                return false;
            }
        };
    }
}
