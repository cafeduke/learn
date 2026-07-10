package io.github.cafeduke.dukecart.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import io.github.cafeduke.dukecart.config.exception.DukeExceptionUtil;
import io.github.cafeduke.dukecart.dto.CustomerProfileDTO;
import io.github.cafeduke.dukecart.entity.Customer;
import io.github.cafeduke.dukecart.repository.AddressRepository;
import io.github.cafeduke.dukecart.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerProfileServiceImpl implements CustomerProfileService
{

  // Dependency Injection -- The injected object should be marked final to make it immutable.
  // ----------------------------------------------------------------------------------------

  private final CustomerRepository customerRepository;

  private final AddressRepository addressRepository;

  @Transactional
  @Override
  public Customer createCustomerProfile(CustomerProfileDTO dto)
  {
    System.out.println("[CAFEDUKE CustomerProfileServiceImpl.createCustomerProfile] CustomerProfileDTO=" + dto.getCustomer());
    final Customer savedCustomer = customerRepository.save(dto.getCustomer());
    updateCustomerDetails(savedCustomer, dto);
    System.out.println("[CAFEDUKE CustomerProfileServiceImpl.createCustomerProfile] Created. SavedCustomer=" + savedCustomer);
    return savedCustomer;
  }

  @Transactional
  @Override
  public CustomerProfileDTO readCustomerProfile(String username)
  {
    final Customer savedCustomer = getSavedCustomer(username);
    CustomerProfileDTO dto = new CustomerProfileDTO();
    dto.setCustomer(savedCustomer);
    dto.setAddresses(addressRepository.findByCustomerId(savedCustomer.getId()));
    return dto;
  }

  @Transactional
  @Override
  public Customer updateCustomerProfile(CustomerProfileDTO dto)
  {
    System.out.println("[CAFEDUKE CustomerProfileServiceImpl.updateCustomerProfile] CustomerProfileDTO=" + dto.getCustomer());

    // Get saved customer from DB
    final Customer savedCustomer = getSavedCustomer(dto.getCustomer().getUsername());
    updateCustomerDetails(savedCustomer, dto);
    System.out.println("[CAFEDUKE CustomerProfileServiceImpl.updateCustomerProfile] Updated. SavedCustomer=" + savedCustomer);
    return savedCustomer;
  }

  @Transactional
  @Override
  public Customer deleteCustomerProfile(String username)
  {
    final Customer savedCustomer = getSavedCustomer(username);

    customerRepository.deleteByUsername(username);
    System.out.println("[CAFEDUKE CustomerProfileServiceImpl.deleteCustomerProfile] Deleted. SavedCustomer=" + savedCustomer);
    return savedCustomer;
  }

  /**
   * Update details for given customer
   *
   * @param savedCustomer Customer fetched from database
   * @param dto The profile dto object
   */
  private void updateCustomerDetails(Customer savedCustomer, CustomerProfileDTO dto)
  {
    List<Long> listAddressIdPrev = addressRepository.findIdsByCustomerId(savedCustomer.getId());
    List<Long> listAddressIdNew = new ArrayList<>();

    // Collect AddressIds from DTO in listAddressIdNew.
    // Set all new addresses as active, update with customer and save them all
    dto.getAddresses().forEach(address -> {
      address.setCustomer(savedCustomer);
      address.setActive(true);
      listAddressIdNew.add(address.getId());
    });
    addressRepository.saveAll(dto.getAddresses());

    // In case of new customer, listAddressIdPrev shall be empty
    if (listAddressIdPrev.isEmpty())
      return;

    // listAddressIdPrev = listAddressIdPrev - listAddressIdNew
    // If aboove set operaitons makes listAddressIdPrev empty then return
    if (!listAddressIdPrev.removeAll(listAddressIdNew) || listAddressIdPrev.isEmpty())
      return;

    // listAddressIdPrev shall have IDs of the previous addresses that the customer is no more interested (and has thus removed them in the latest udpate)
    // We shall mark these addresses (if any) as inactive
    listAddressIdPrev
      .stream()
      .map(addressRepository::getReferenceById)
      .forEach(address -> {
        address.setActive(false);
        addressRepository.save(address);
      });
  }

  /**
   * Get the customer having <b>username</b> from database.
   * Throw DukeResourceNotFoundException if not found.
   *
   * @param username Login username of the customer
   * @return Customer having the username
   */
  private Customer getSavedCustomer(String username)
  {
    System.out.println("[CAFEDUKE CustomerProfileServiceImpl.getSavedCustomer] username=" + username);
    final String mesg = String.format("Customer username=%s does not exist in database", username);

    final Customer savedCustomer = customerRepository
      .findByUsername(username)
      .orElseThrow(DukeExceptionUtil.notFound(mesg));

    return savedCustomer;
  }
}
