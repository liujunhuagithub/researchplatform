package cn.edu.ncepu.researchplatform.mapper

import cn.edu.ncepu.researchplatform.entity.Evaluate
import cn.edu.ncepu.researchplatform.entity.Summary
import cn.edu.ncepu.researchplatform.entity.dto.SummaryDto
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
interface SummaryMapper {
    @Insert('insert into `summary`(people_id,content) values (#{peopleId},#{content}) ')
    @Options(keyColumn = "id", keyProperty = "id", useGeneratedKeys = true)
    Integer insert(Summary summary);

    @Select('select * from summary where id=#{param1}')
    Summary findById(Integer summaryId);

    @Select('''
<script>
SELECT
summary.id as id, 
summary.people_id as people_id,
summary.gmt_create as gmt_create, 
summary.content as content
FROM
summary
INNER JOIN
people
ON 
summary.people_id = people.id
<where>       
            <if test="lc!=null">
                and gmt_create &gt;=#{lc}
            </if>
             <if test="rc!=null">
                and gmt_create &lt;=#{rc}
            </if>
            <if test="username !=null and username !=''">
                and people.username like #{username}
            </if>
</where>
limit ${(current-1)*size},#{size}
</script>''')
    List<Summary> findByCondition(SummaryDto dto);

    @Select('''
<script>
SELECT
summary.id as id, 
summary.gmt_create as gmt_create, 
summary.content as content, 
people.username as username, 
people.nickname as nickname
FROM
summary
INNER JOIN
people
ON 
summary.people_id = people.id
<where>       
            <if test="lc!=null">
                and gmt_create &gt;=#{lc}
            </if>
             <if test="rc!=null">
                and gmt_create &lt;=#{rc}
            </if>
            <if test="username !=null and username !=''">
                and people.username like #{username}
            </if>
</where>
</script>''')
    Integer findCountByCondition(SummaryDto dto);

    @Update('update `evaluate`set summary_id=#{summaryId} where id=#{id}')
    boolean updateEvaluateSummaryId(Evaluate evaluate);
}




