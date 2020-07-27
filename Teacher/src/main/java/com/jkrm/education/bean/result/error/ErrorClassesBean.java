package com.jkrm.education.bean.result.error;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/23 16:49
 */

public class ErrorClassesBean {

    /**
     * code : 200
     * data : [{"id":"c532af31e08b4d749a11b3befce619e2","name":"七年级2班"}]
     * msg :
     */


        /**
         * id : c532af31e08b4d749a11b3befce619e2
         * name : 七年级2班
         */

        private String id;
        private String name;
        private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
}
