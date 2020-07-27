package com.jkrm.education.bean.result;

/**
 * Created by hzw on 2019/6/11.
 */

public class TeacherTodoBean {

    private String todoId;
    private String name;
    private String className;
    private String flag;
    private String time;
    private String homeworkId;
    private String classId;
    private String population;
    private String submitted;
    private String condProgress;
    private String progress;
    private String unprogress;
    private String effect;//1处理完毕, 0处理中

    //自定义开始.......................................................................

    private int idBg; //序号背景色
    private boolean isAllowOperate; //是否允许操作, 该字段目前暂无实际使用之处, 保留

    public int getIdBg() {
        return idBg;
    }

    public void setIdBg(int idBg) {
        this.idBg = idBg;
    }

    public boolean isAllowOperate() {
        return true;//直接返回true, 目前版本暂无实际用处
    }

    public void setAllowOperate(boolean allowOperate) {
        isAllowOperate = allowOperate;
    }

    public boolean isHandle() {
        return "1".equals(effect);
    }

    //自定义结束........................................................................

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getUnprogress() {
        return unprogress;
    }

    public void setUnprogress(String unprogress) {
        this.unprogress = unprogress;
    }

    public String getTodoId() {
        return todoId;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getCondProgress() {
        return condProgress;
    }

    public void setCondProgress(String condProgress) {
        this.condProgress = condProgress;
    }
}
