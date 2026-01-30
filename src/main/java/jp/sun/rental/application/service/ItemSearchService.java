package jp.sun.rental.application.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jp.sun.rental.domain.entity.ItemEntity;
import jp.sun.rental.domain.repository.ItemRepository;
import jp.sun.rental.presentation.form.ItemForm;

@Service
public class ItemSearchService {

	private ItemRepository itemRepository;
	private ModelMapper modelMapper;
	
	public ItemSearchService(ItemRepository itemRepository, ModelMapper modelMapper) {
		this.itemRepository = itemRepository;
		this.modelMapper = modelMapper;
	}
	
	public List<ItemForm> getItemsList(ItemForm form) throws Exception {
		
		List<ItemEntity> entityList = null;
		List<ItemForm> formList = null;
		
		String itemName = form.getItemName();
		
		if(!itemName.isEmpty()) {
			entityList = itemRepository.getItemsByName(itemName);
		}else {
			entityList = itemRepository.getItemsAllList();
		}
		
		formList = convert(entityList);
		
		return formList;
	}
	
	private List<ItemForm> convert(List<ItemEntity> entityList){
		
		List<ItemForm> formList = new ArrayList<ItemForm>();
		
		for (ItemEntity entity : entityList) {
			ItemForm form = modelMapper.map(entity, ItemForm.class);
			formList.add(form);
		}
		
		return formList;
	}
}
