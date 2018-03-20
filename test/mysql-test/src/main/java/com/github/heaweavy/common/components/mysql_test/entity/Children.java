package com.github.heaweavy.common.components.mysql_test.entity;

/**
 * @author caimb
 * @date Created in 2017/12/4 22:37
 * @modefier
 */
public class Children {
    private Long id;
    private Long parentId;
    private Long userId;
    private Boolean enabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
