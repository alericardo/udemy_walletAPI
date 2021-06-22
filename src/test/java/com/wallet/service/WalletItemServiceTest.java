package com.wallet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import com.wallet.entities.Wallet;
import com.wallet.entities.WalletItem;
import com.wallet.repository.WalletItemRepository;
import com.wallet.util.enums.TypeEnum;

@SpringBootTest
@ActiveProfiles("test")
public class WalletItemServiceTest {

	private static final Date DATE =new Date();
	private static final TypeEnum TYPE = TypeEnum.EN;
	private static final String DESCRIPTION = "Conta de luz";
	private static final BigDecimal VALUE = BigDecimal.valueOf(65);

	@MockBean
	private WalletItemRepository repository;
	
	@Autowired
	private WalletItemService service;
		
	@Test
	public void testSave() {
		BDDMockito.given(repository.save(any(WalletItem.class))).willReturn(getMockWalletItem());
				
		WalletItem response = service.save(new WalletItem());
		
		assertNotNull(response);
		assertEquals(DESCRIPTION, response.getDescription());
		assertEquals(0, response.getValue().compareTo(VALUE));
	}
	
	@Test
	public void testFindBetweenDates() {
		List<WalletItem> list = new ArrayList<>();
		list.add(getMockWalletItem());
		Page<WalletItem> page = new PageImpl<>(list);
		
		BDDMockito.given(repository.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(anyLong(), any(Date.class),  any(Date.class),  any(Pageable.class)))
			.willReturn(page);
		
		Page<WalletItem> response = service.findBetweenDates(1l, new Date(), new Date(), 0);
		
		assertNotNull(response);
		assertEquals(1, response.getTotalElements());
		assertEquals(DESCRIPTION, response.getContent().get(0).getDescription());
	}
	
	@Test
	public void testFindByType() {
		List<WalletItem> list = new ArrayList<>();
		list.add(getMockWalletItem());
		
		BDDMockito.given(repository.findByWalletIdAndType(anyLong(), any(TypeEnum.class))).willReturn(list);
		
		List<WalletItem> response = service.findByWalletAndType(1L, TypeEnum.EN);
		
		assertNotNull(response);
		assertEquals(TYPE, response.get(0).getType());
	}
	
	@Test
	public void testSumByWallet() {
		BigDecimal value = BigDecimal.valueOf(45);

		BDDMockito.given(repository.sumByWalletId(anyLong())).willReturn(value);
		
		BigDecimal response = service.sumByWalletId(1L);
		
		assertEquals(0, response.compareTo(value));
	}
	
	private WalletItem getMockWalletItem() {
		Wallet w = new Wallet();
		w.setId(1l);
		return new WalletItem(1l, w, DATE, TYPE, DESCRIPTION, VALUE);
	}

}
