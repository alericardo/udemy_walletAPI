package com.wallet.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import com.wallet.entities.Wallet;
import com.wallet.entities.WalletItem;
import com.wallet.util.enums.TypeEnum;

@SpringBootTest
@ActiveProfiles("test")
public class WalletItemRepositoryTest {

	private static final Date DATE =new Date();
	private static final TypeEnum TYPE = TypeEnum.EN;
	private static final String DESCRIPTION = "Conta de luz";
	private static final BigDecimal VALUE = BigDecimal.valueOf(65);
	private Long savedWalletItemId = null;
	private Long savedWalletId = null;
	
	@Autowired
	private WalletItemRepository repository;
	
	@Autowired
	private WalletRepository walletRepository;
		
	@BeforeEach
	public void setup() {
		Wallet w = new Wallet();
		w.setName("Carteira Teste");
		w.setValue(BigDecimal.valueOf(500));
		walletRepository.save(w);
	
		WalletItem wi = new WalletItem(null, w, DATE, TYPE, DESCRIPTION, VALUE);
		repository.save(wi);
		
		this.savedWalletItemId = wi.getId();
		this.savedWalletId = w.getId();		
	}
	
	@BeforeEach
	public void tearDown() {
		repository.deleteAll();
		walletRepository.deleteAll();
	}
	
	@Test
	public void testSave() {
		
		Wallet w = new Wallet();
		w.setName("Carteira 1");
		w.setValue(BigDecimal.valueOf(500));
		walletRepository.save(w);
	
		WalletItem wi = new WalletItem(null, w, DATE, TYPE, DESCRIPTION, VALUE);
		
		WalletItem response = repository.save(wi);
		
		assertNotNull(response);
		assertEquals(DESCRIPTION, response.getDescription());
		assertEquals(TYPE, response.getType());
		assertEquals(VALUE, response.getValue());
		assertEquals(w.getId(), response.getWallet().getId());
	}
	
	@Test
	public void testSaveInvalidWalletItem() {
		WalletItem wi = new WalletItem(null, null, DATE, null, DESCRIPTION, null);
		assertThrows(ConstraintViolationException.class, () -> repository.save(wi));		
	}
	
	@Test
	public void testUpdate() {
		Optional<WalletItem> wi = repository.findById(savedWalletItemId);
		
		String description = "Descri????o alterada";
		
		WalletItem changed = wi.get();
		changed.setDescription(description);
		
		repository.save(changed);
		
		Optional<WalletItem> newWalletItem = repository.findById(savedWalletItemId);
		
		assertEquals(description, newWalletItem.get().getDescription());
	}
	
	@Test
	public void testDeleteWalletItem() {
		Optional<Wallet> wallet = walletRepository.findById(savedWalletId);
		WalletItem wi = new WalletItem(null, wallet.get(), DATE, TYPE, DESCRIPTION, VALUE);
		
		repository.save(wi);
		
		repository.deleteById(wi.getId());
		
		Optional<WalletItem> response = repository.findById(wi.getId());
		
		assertFalse(response.isPresent());
	}
	
	@Test
	public void testFindBetweenDates() {
		Optional<Wallet> w = walletRepository.findById(savedWalletId);
		
		LocalDateTime localDateTime = DATE.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		
		Date currentDatePlusFiveDays = Date.from(localDateTime.plusDays(5).atZone(ZoneId.systemDefault()).toInstant());
		Date currentDatePlusSevenDays = Date.from(localDateTime.plusDays(7).atZone(ZoneId.systemDefault()).toInstant());
		
		repository.save(new WalletItem(null, w.get(), currentDatePlusFiveDays, TYPE, DESCRIPTION, VALUE));
		repository.save(new WalletItem(null, w.get(), currentDatePlusSevenDays, TYPE, DESCRIPTION, VALUE));
		
		PageRequest pg = PageRequest.of(0, 10);
		Page<WalletItem> response = repository.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(savedWalletId, DATE, currentDatePlusFiveDays, pg);
		
		assertEquals(2, response.getContent().size());
		assertEquals(2, response.getTotalElements());
		assertEquals(savedWalletId, response.getContent().get(0).getWallet().getId());
	}
	
	@Test
	public void testeFindByType() {
		List<WalletItem> response = repository.findByWalletIdAndType(savedWalletId, TYPE);
		
		assertEquals(1, response.size());
		assertEquals(TYPE, response.get(0).getType());
	}
	
	@Test
	public void testeFindByTypeSd() {
		Optional<Wallet> wallet = walletRepository.findById(savedWalletId);
		WalletItem wi = new WalletItem(null, wallet.get(), DATE, TypeEnum.SD, DESCRIPTION, VALUE);
		
		repository.save(wi);
		
		List<WalletItem> response = repository.findByWalletIdAndType(savedWalletId, TypeEnum.SD);
		
		assertEquals(1, response.size());
		assertEquals(TypeEnum.SD, response.get(0).getType());
	}
	
	@Test
	public void testeSumByWallet() {
		
		Optional<Wallet> w = walletRepository.findById(savedWalletId);
		
		BigDecimal newValue = BigDecimal.valueOf(150.8);
		
		repository.save(new WalletItem(null, w.get(), DATE, TYPE, DESCRIPTION, newValue));
		
		BigDecimal response = repository.sumByWalletId(savedWalletId);
		
		assertEquals(0, response.compareTo(newValue.add(VALUE)));
		
	}
}
