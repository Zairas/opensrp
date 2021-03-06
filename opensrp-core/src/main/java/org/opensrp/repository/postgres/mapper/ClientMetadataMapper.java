package org.opensrp.repository.postgres.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.opensrp.domain.postgres.ClientMetadata;
import org.opensrp.domain.postgres.ClientMetadataExample;

public interface ClientMetadataMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table core.client_metadata
	 * @mbg.generated  Fri Apr 06 15:32:26 EAT 2018
	 */
	long countByExample(ClientMetadataExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table core.client_metadata
	 * @mbg.generated  Fri Apr 06 15:32:26 EAT 2018
	 */
	int deleteByExample(ClientMetadataExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table core.client_metadata
	 * @mbg.generated  Fri Apr 06 15:32:26 EAT 2018
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table core.client_metadata
	 * @mbg.generated  Fri Apr 06 15:32:26 EAT 2018
	 */
	int insert(ClientMetadata record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table core.client_metadata
	 * @mbg.generated  Fri Apr 06 15:32:26 EAT 2018
	 */
	int insertSelective(ClientMetadata record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table core.client_metadata
	 * @mbg.generated  Fri Apr 06 15:32:26 EAT 2018
	 */
	List<ClientMetadata> selectByExample(ClientMetadataExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table core.client_metadata
	 * @mbg.generated  Fri Apr 06 15:32:26 EAT 2018
	 */
	ClientMetadata selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table core.client_metadata
	 * @mbg.generated  Fri Apr 06 15:32:26 EAT 2018
	 */
	int updateByExampleSelective(@Param("record") ClientMetadata record, @Param("example") ClientMetadataExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table core.client_metadata
	 * @mbg.generated  Fri Apr 06 15:32:26 EAT 2018
	 */
	int updateByExample(@Param("record") ClientMetadata record, @Param("example") ClientMetadataExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table core.client_metadata
	 * @mbg.generated  Fri Apr 06 15:32:26 EAT 2018
	 */
	int updateByPrimaryKeySelective(ClientMetadata record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table core.client_metadata
	 * @mbg.generated  Fri Apr 06 15:32:26 EAT 2018
	 */
	int updateByPrimaryKey(ClientMetadata record);
}