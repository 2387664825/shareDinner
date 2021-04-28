package com.hfy.dinner.repository.pojo;

import com.github.pagehelper.PageInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hufayong
 * @date 2021/3/6 16:22
 */
@Getter
@Setter
public class ResponseDo {
    /**
     * 分页信息
     */
    private PagingInfo pageInfo;

    /**
     * 数据集合
     */
    private Object data;

    /**
     * 返回状态
     */
    private Integer status;

    /**
     * 构建含分页信息的响应对象.
     *
     * @param pageInfo pageInfo
     */
    public ResponseDo(PageInfo<?> pageInfo) {
        this.setPageInfo(new PagingInfo(pageInfo));
        this.setData(pageInfo.getList());
    }

    /**
     * 构建不含分页信息的响应对象.
     *
     * @param data 数据
     */
    public ResponseDo(Integer status, Object data) {
        this.status = status;
        this.data = data;
    }

    @Getter
    @Setter
    class PagingInfo {
        /**
         * 每页条数
         */
        private Integer limit;

        /**
         * 当前页数
         */
        private Integer offset;

        /**
         * 总页数
         */
        private Integer pageCount;

        /**
         * 总记录数
         */
        private Long rowCount;

        /**
         * 构造方法.
         *
         * @param pageInfo 分页信息
         */
        PagingInfo(PageInfo<?> pageInfo) {
            if (pageInfo == null) {
                throw new IllegalArgumentException("PageInfo is null");
            }

            this.setLimit(pageInfo.getPageSize());
            int offsetTemp = pageInfo.getStartRow() - 1;
            this.setOffset(offsetTemp > 0 ? offsetTemp : 0);
            this.setPageCount(pageInfo.getPages());
            this.setRowCount(pageInfo.getTotal());
        }
    }
}
