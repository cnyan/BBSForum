package com.yan.domain;

/**
 * Created by 闫继龙 on 2017/7/1.
 * MainPost主题对应的主题帖
 */

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 *<br>
 * <b>类描述:</b>
 *
 * <pre>
 * 主题对应的主题帖
 * </pre>
 *
 * @see
 *@since
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "post_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("2")
public class MainPost extends BaseDomain {

}
