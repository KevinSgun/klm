package cn.kuailaimei.store.turnover.datasource;

import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;
import com.zitech.framework.data.network.response.ApiResponse;

import java.util.List;

import cn.kuailaimei.store.api.ApiFactory;
import cn.kuailaimei.store.api.entity.AmountItem;
import cn.kuailaimei.store.api.request.PageRequest;
import cn.kuailaimei.store.api.response.AmountListResponse;
import cn.kuailaimei.store.common.datasource.PagedProxy;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by ymh on 2016/7/3 0003.
 */
public class EmpTotalOrderCountDataSource implements IAsyncDataSource<List<AmountItem>> {
    private PagedProxy proxy = new PagedProxy(20);
    private PageRequest request;

    public EmpTotalOrderCountDataSource(PageRequest pageRequest) {
        super();
        this.request = pageRequest;
    }

    @Override
    public RequestHandle refresh(ResponseSender<List<AmountItem>> sender) throws Exception {
        return loadStores(sender, proxy.reset());
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<AmountItem>> sender) throws Exception {
        return loadStores(sender, proxy.toNextPage());
    }

    @Override
    public boolean hasMore() {
        return proxy.hasNextPage();
    }

    private RequestHandle loadStores(final ResponseSender<List<AmountItem>> sender, final int page) throws Exception {
        request.setCurrentPage(page);
        final Subscription subscribe = ApiFactory.getEmpTotalCountOrderList(request).subscribe(new Action1<ApiResponse<AmountListResponse>>() {
            @Override
            public void call(ApiResponse<AmountListResponse> response) {
                AmountListResponse data = response.getData();
                if (!proxy.isPageCountSet()) {
                    proxy.setDataCount(data.getPagination().getRows());
                }
                List<AmountItem> items = data.getList();
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
