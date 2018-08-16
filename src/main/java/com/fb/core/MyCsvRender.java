package com.fb.core;

import com.fb.commons.MyCsvUtil;
import com.jfinal.render.Render;
import com.jfinal.render.RenderException;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by shi.g.s on 2017/10/27.
 */
public class MyCsvRender extends Render{
    private List<String> clomuns;
    private List<?> data;
    private String encodeType = "gbk";
    private String fileName = "default.csv";
    private List<String> headers;

    public MyCsvRender(List<String> headers, List<?> data) {
        this.headers = headers;
        this.data = data;
    }

    public static MyCsvRender me(List<String> headers, List<?> data) {
        return new MyCsvRender(headers, data);
    }

    @Override
    public void render() {
        response.reset();
        PrintWriter out = null;
        try {
            response.setContentType("application/vnd.ms-excel;charset=" + encodeType);
            response.setHeader("Content-Disposition",
                    "attachment;  filename=" + URLEncoder.encode(fileName, encodeType));
            out = response.getWriter();
            out.write(MyCsvUtil.createCSV(headers, data, clomuns));
        } catch (Exception e) {
            throw new RenderException(e);
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }

    public MyCsvRender clomuns(List<String> clomuns) {
        this.clomuns = clomuns;
        return this;
    }

    public MyCsvRender data(List<? extends Object> data) {
        this.data = data;
        return this;
    }

    public MyCsvRender encodeType(String encodeType) {
        this.encodeType = encodeType;
        return this;
    }

    public MyCsvRender fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public MyCsvRender headers(List<String> headers) {
        this.headers = headers;
        return this;
    }
}
