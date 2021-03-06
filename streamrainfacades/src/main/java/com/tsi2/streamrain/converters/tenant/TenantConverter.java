package com.tsi2.streamrain.converters.tenant;


import java.util.ArrayList;
import java.util.List;

import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.datatypes.tenant.TenantDto;
import com.tsi2.streamrain.model.main.Tenants;

public class TenantConverter implements IConverter<TenantDto, Tenants>{

	public TenantDto converter(Tenants source) {
		TenantDto target = new TenantDto();
		target.setId(source.getId());
		target.setDbIp(source.getDbIp());
		target.setName(source.getName());
		target.setDbPassword(source.getDbPassword());
		target.setDbPort(source.getDbPort());
		target.setDbUser(source.getDbUser());
		target.setGeneratorPassword(source.getGeneratorPassword());
		target.setGeneratorUser(source.getGeneratorUser());
		return target;
	}

	public Tenants deConverter(TenantDto source) {
		Tenants target = new Tenants();
		target.setId(source.getId());
		target.setDbIp(source.getDbIp());
		target.setName(source.getName());
		target.setDbPassword(source.getDbPassword());
		target.setDbPort(source.getDbPort());
		target.setDbUser(source.getDbUser());
		target.setGeneratorPassword(source.getGeneratorPassword());
		target.setGeneratorUser(source.getGeneratorUser());
		return target;
	}

	public List<TenantDto> convertAll(List<Tenants> source) {
		List<TenantDto> dtoList = new ArrayList<TenantDto>();
		for(Tenants tenant : source) {
			dtoList.add(converter(tenant));
		}
		return dtoList;
	}

	public List<Tenants> deConvertAll(List<TenantDto> source) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
