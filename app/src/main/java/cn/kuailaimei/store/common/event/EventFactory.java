package cn.kuailaimei.store.common.event;

/**
 * Created by ymh on 2016/7/6 0006.
 */
public class EventFactory {

    public static class CloseAll {

    }

    public static class UserDataChange {

    }

    public static class ItemUpdate{

    }

    public static class EmpUpdate{

    }

    public static class BindingChange{

    }

    public static class AccountChange{

    }

    public static class EditContent{
        /**编辑的内容*/
        public String editContent;
    }

    /**
     * 默认都更新，不需要更新的传一个true
     */
    public static class OrderDataChange{
//      public boolean allNoUpdate;
//        public boolean waitPayNoUpdate;
//        public boolean waitConfirmNoUpdate;
//        public boolean completeNoUpdate;
    }
}
