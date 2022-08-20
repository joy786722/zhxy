package com.school.zhxy.controller;

import com.school.zhxy.pojo.Admin;
import com.school.zhxy.pojo.LoginForm;
import com.school.zhxy.pojo.Student;
import com.school.zhxy.pojo.Teacher;
import com.school.zhxy.service.AdminSercice;
import com.school.zhxy.service.TeacherService;
import com.school.zhxy.util.CreateVerifiCodeImage;
import com.school.zhxy.service.StudentService;

import com.school.zhxy.util.JwtHelper;
import com.school.zhxy.util.Result;
import com.school.zhxy.util.ResultCodeEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/sms/system")
public class SystemController {
    @Autowired
    private AdminSercice adminSercice;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    @RequestMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response) {
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        String s = String.valueOf(CreateVerifiCodeImage.getVerifiCode());
        request.getSession().setAttribute("VerifiCode", s);
        try {
            ImageIO.write(verifiCodeImage, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/login")
    public Result login(HttpServletRequest request, @RequestBody LoginForm loginForm) {
        HttpSession session = request.getSession();
        String sessionVerifiCode = (String) session.getAttribute("VerifiCode");
        String loginVerifiCode = loginForm.getVerifiCode();
        if ("".equals(sessionVerifiCode) || null == sessionVerifiCode) {
            return Result.fail().message("验证码过期或为空");
        }
        if (!loginVerifiCode.equals(sessionVerifiCode)) {
            return Result.fail().message("验证码输入错误");
        }
        session.removeAttribute("VerifiCode");
        Map<String, Object> map = new LinkedHashMap<>();

        switch (loginForm.getUserType()) {
            case 1:
                Admin admin = adminSercice.login(loginForm);
                try {
                    if (null != admin) {
                        map.put("token", JwtHelper.createToken(admin.getId().longValue(), 1));
                    } else {
                        throw new RuntimeException("密码或账号有误");
                    }
                    return Result.ok(map);
                } catch (Exception e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 2:
                Student student = studentService.login(loginForm);
                try {
                    if (null != student) {
                        map.put("token", JwtHelper.createToken(student.getId().longValue(), 2));
                    } else {
                        throw new RuntimeException("密码或账号有误");
                    }
                    return Result.ok(map);
                } catch (Exception e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 3:
                Teacher teacher = teacherService.login(loginForm);
                try {
                    if (null != teacher) {
                        map.put("token", JwtHelper.createToken(teacher.getId().longValue(), 3));
                    } else {
                        throw new RuntimeException("密码或账号有误");
                    }
                    return Result.ok(map);
                } catch (Exception e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            default:
                return Result.fail().message("无此用户");
        }

    }

    @RequestMapping("/getInfo")
    public Result getInfo(@RequestHeader("token") String token, HttpServletResponse response) {
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration) {
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        Integer userType = JwtHelper.getUserType(token);
        Long userId = JwtHelper.getUserId(token);
        Map map = new LinkedHashMap();
        switch (userType) {

            case 1:
                Admin admin = adminSercice.selectByid(userId);
                map.put("user", admin);
                map.put("userType", 1);
                break;
            case 2:
                Student student = studentService.selectByid(userId);
                map.put("user", student);
                map.put("userType", 2);
                break;
            case 3:
                Teacher teacher = teacherService.selectByid(userId);
                map.put("user", teacher);
                map.put("userType", 3);
                break;
        }
        return Result.ok(map);
    }

    @RequestMapping("/headerImgUpload")
    public Result headerImgUpload(@RequestPart("multipartFile") MultipartFile multipartFile) throws IOException {
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        //生成新的文件名字
        String filename = uuid.concat(multipartFile.getOriginalFilename());
        System.out.println(filename);

//        java.io.IOException: java.io.FileNotFoundException: C:\Users\admin\AppData\Local\Temp\tomcat.9001.3980416926906985669\work\Tomcat\localhost\ROOT\
//        C:\Users\admin\IdeaProjects\zhxy\target\classes\public\upload\147dc0d1cd404591a3e10da82d63eb9c1.jpg (文件名、目录名或卷标语法不正确。)
        String portraitPath = " C:/Users/admin/IdeaProjects/zhxy/target/classes/public/upload/".concat(filename);
        //保存文件
        try {
            multipartFile.transferTo(new File(portraitPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String headerImg = "upload/" + filename;
        System.out.println(headerImg);
        return Result.ok(headerImg);

    }
}
