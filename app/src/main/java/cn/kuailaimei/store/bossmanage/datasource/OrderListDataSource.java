package cn.kuailaimei.store.bossmanage.datasource;

import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;
import com.zitech.framework.data.network.response.ApiResponse;

import java.util.List;

import cn.kuailaimei.store.api.ApiFactory;
import cn.kuailaimei.store.api.entity.OrderItem;
import cn.kuailaimei.store.api.request.PageRequest;
import cn.kuailaimei.store.api.response.OrderListResponse;
import cn.kuailaimei.store.common.User;
import cn.kuailaimei.store.common.datasource.PagedProxy;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by ymh on 2016/7/3 0003.
 */
public class OrderListDataSource implements IAsyncDataSource<List<OrderItem>> {
    private PagedProxy proxy = new PagedProxy(20);
    private PageRequest request;

    public OrderListDataSource(PageRequest pageRequest) {
        super();
        this.request = pageRequest;
    }

    @Override
    public RequestHandle refresh(ResponseSender<List<OrderItem>> sender) throws Exception {
        return loadStores(sender, proxy.reset());
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<OrderItem>> sender) throws Exception {
        return loadStores(sender, proxy.toNextPage());
    }

    @Override
    public boolean hasMore() {
        return proxy.hasNextPage();
    }

    private RequestHandle loadStores(final ResponseSender<List<OrderItem>> sender, final int page) throws Exception {
        request.setCurrentPage(page);
        final Subscription subscribe = User.get().isBoss()?getBossOrderList(sender):getEmpOrderList(sender);


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

    private Subscription getEmpOrderList(final ResponseSender<List<OrderItem>> sender) {
        return ApiFactory.getEmpOrderList(request).subscribe(new Action1<ApiResponse<OrderListResponse>>() {
            @Override
            public void call(ApiResponse<OrderListResponse> response) {
                OrderListResponse data = response.getData();
                if (!proxy.isPageCountSet()) {
                    proxy.setDataCount(data.getPagination().getRows());
                }
                List<OrderItem> items = data.getList();
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
    }

    private Subscription getBossOrderList(final ResponseSender<List<OrderItem>> sender) {
        return ApiFactory.getOrderList(request).subscribe(new Action1<ApiResponse<OrderListResponse>>() {
            @Override
            public void call(ApiResponse<OrderListResponse> response) {
                OrderListResponse data = response.getData();
                if (!proxy.isPageCountSet()) {
                    proxy.setDataCount(data.getPagination().getRows());
                }
                List<OrderItem> items = data.getList();
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
    }
}
