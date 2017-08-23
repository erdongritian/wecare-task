package com.htxa.wecare.controller;

import com.htxa.wecare.model.CronTriggerWithState;
import com.htxa.wecare.model.TaskEntity;
import com.htxa.wecare.util.CommonUtil;
import com.htxa.wecare.util.TaskUtil;
import com.htxa.wecare.util.WebSecurityConfig;
import org.quartz.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author <a href="mailto:erdongritian@foxmail.com">chenhao</a>
 *         Create Time:2017/8/9 17:10
 */
@RestController
@RequestMapping("/")
public class MainController {

    @Resource
    private TaskUtil taskUtil;

    @RequestMapping("/")
    public ModelAndView index(){
        ModelAndView mv=new ModelAndView("login");
        return mv;
    }

    @RequestMapping(value="/login", method= RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> login(@RequestParam("username")String username, @RequestParam("password")String password, HttpSession session) {
        if (!"admin".equals(username)) {
            return CommonUtil.commonResponse(false,"The username error.");
        }

        if (!"1".equals(password)) {
            return CommonUtil.commonResponse(false,"The password error.");
        }

        // 设置session
        session.setAttribute(WebSecurityConfig.SESSION_KEY, username);
        return CommonUtil.commonResponse(true,"Login success.");
    }

    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public void logout( HttpSession session,HttpServletResponse response) throws Exception {
        session.removeAttribute(WebSecurityConfig.SESSION_KEY);
        response.sendRedirect("/");
    }


    @RequestMapping(value="/task/list", method= RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ModelAndView list() {
        ModelAndView mv=new ModelAndView("tasklist");
        List<CronTriggerWithState> tasks=taskUtil.getAllTask();
        mv.addObject("list",tasks);
        return mv;
    }

    @RequestMapping(value="/task/add", method= RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> addTask(TaskEntity taskEntity){
        Boolean isJob=taskUtil.testTask(taskEntity.getTaskService());
        if(!isJob){
            return CommonUtil.commonResponse(false,"Add error,Service Name must be the spirng bean that is implements Job.");
        }
        String result=taskUtil.addTask(taskEntity);
        if("SUCCESS".equals(result)){
            return CommonUtil.commonResponse(true,"Add success");
        }else {
            return CommonUtil.commonResponse(false,"Add error,"+result);
        }
    }

    @RequestMapping(value="/task/modify", method= RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> modifyTask(TaskEntity taskEntity) throws SchedulerException {
        String result=taskUtil.modifySchedule(taskEntity);
        if("SUCCESS".equals(result)){
            return CommonUtil.commonResponse(true,"Modify success");
        }else {
            return CommonUtil.commonResponse(false,"Modify error,"+result);
        }
    }

    @RequestMapping(value="/task/pause/{servicename}", method= RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Map<String, Object> pauseTask(@PathVariable String servicename) {
        Boolean result=taskUtil.pauseTase(servicename);
        if(result){
            return CommonUtil.commonResponse(true,"Pause success");
        }else {
            return CommonUtil.commonResponse(false,"Pause error");
        }
    }

    @RequestMapping(value="/task/play/{servicename}", method= RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Map<String, Object> playTask(@PathVariable String servicename) {
        Boolean result=taskUtil.resumeTask(servicename);
        if(result){
            return CommonUtil.commonResponse(true,"Play success");
        }else {
            return CommonUtil.commonResponse(false,"Play error");
        }
    }

    @RequestMapping(value="/task/delete/{servicename}", method= RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Map<String, Object> deleteTask(@PathVariable String servicename) {
        Boolean result=taskUtil.deleteTask(servicename);
        if(result){
            return CommonUtil.commonResponse(true,"Delete success");
        }else {
            return CommonUtil.commonResponse(false,"Delete error");
        }
    }

    @RequestMapping(value="/task/test/{servicename}", method= RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Map<String, Object> testTask(@PathVariable String servicename) {
        Boolean result=taskUtil.testTask(servicename);
        if(result){
            return CommonUtil.commonResponse(true,"Test success");
        }else {
            return CommonUtil.commonResponse(false,"Test error");
        }
    }
}
