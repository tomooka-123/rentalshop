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
	
	//フォームの情報を元に商品を検索する
	public List<ItemForm> getItemsList(ItemForm form) throws Exception {
		
		List<ItemEntity> entityList = null;
		List<ItemForm> formList = null;
		
		String itemName = form.getItemName();
		
		//商品名が空なら全件検索、そうでないなら入力内容に応じてあいまい検索
		if(!itemName.isEmpty()) {
			entityList = itemRepository.getItemsByName(itemName);
		}else {
			entityList = itemRepository.getItemsAllList();
		}
		
		formList = convert(entityList);
		
		return formList;
	}
	
	//DBから取得したエンティティをhtml出力用のフォームに変換
	private List<ItemForm> convert(List<ItemEntity> entityList){
		
		List<ItemForm> formList = new ArrayList<ItemForm>();
		
		for (ItemEntity entity : entityList) {
			ItemForm form = modelMapper.map(entity, ItemForm.class);
			formList.add(form);
		}
		
		return formList;
	}
}
