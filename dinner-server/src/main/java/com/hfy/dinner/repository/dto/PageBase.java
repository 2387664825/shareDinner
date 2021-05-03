package com.hfy.dinner.repository.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
/**
 * @author hfy
 * @date 2021/5/2 9:36
 */
@Data
public class PageBase {

    /**
     * 降序.
     */
    private static final String SORT_DESC = "-";

    /**
     * 每页条数.
     */
    private Integer limit;

    /**
     * 当前页.
     */
    private Integer offset;

    /**
     * 排序字段.
     */
    @JsonIgnore
    private String order;

    /**
     * 解析排序字段.
     *
     * @param order order
     */
//    public void setOrder(String order) {
//        if (StringUtils.isNotBlank(order)) {
//            String[] sorts = order.split(",");
//            StringBuilder sbSort = new StringBuilder();
//            for (String str : sorts) {
//                str = str.trim();
//                // "-"表示倒序
//                if (str.startsWith(SORT_DESC)) {
//                    sbSort.append(FiledPropMapping.getFiledProp()
//                            .get(StringUtils.substringAfterLast(str, "-"))).append(" desc");
//                } else {
//                    sbSort.append(FiledPropMapping.getFiledProp().get(str)).append(" asc");
//                }
//                sbSort.append(",");
//            }
//            this.order = sbSort.deleteCharAt(sbSort.length() - 1).toString();
//        }
//    }

}
