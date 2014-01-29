/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.consent2share.service.provider;

import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.domain.provider.IndividualProvider;
import gov.samhsa.consent2share.domain.provider.IndividualProviderRepository;
import gov.samhsa.consent2share.domain.provider.StaffIndividualProvider;
import gov.samhsa.consent2share.domain.provider.StaffIndividualProviderRepository;
import gov.samhsa.consent2share.domain.reference.EntityType;
import gov.samhsa.consent2share.service.dto.AbstractProviderDto;
import gov.samhsa.consent2share.service.dto.IndividualProviderDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * The Class IndividualProviderServiceImpl.
 */
@Service
@Transactional
public class IndividualProviderServiceImpl implements IndividualProviderService {

	/** The individual provider repository. */
	@Autowired
    IndividualProviderRepository individualProviderRepository;
	
	/** The model mapper. */
	@Autowired
	ModelMapper modelMapper;
	
	/** The patient repository. */
	@Autowired
	PatientRepository patientRepository;
	
	@Autowired
	StaffIndividualProviderRepository staffIndividualProviderRepository;

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#countAllIndividualProviders()
	 */
	public long countAllIndividualProviders() {
        return individualProviderRepository.count();
    }
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#updateIndividualProvider(gov.samhsa.consent2share.service.dto.IndividualProviderDto)
	 */
	public void updateIndividualProvider(IndividualProviderDto individualProviderDto){
		IndividualProvider individualProvider=individualProviderRepository.findByPatientAndNpi(
				patientRepository.findByUsername(individualProviderDto.getUsername()), 
				individualProviderDto.getNpi());
		if (individualProvider==null)
			individualProvider=new IndividualProvider();
		individualProvider.setFirstName(individualProviderDto.getFirstName());
		individualProvider.setMiddleName(individualProviderDto.getMiddleName());
		individualProvider.setLastName(individualProviderDto.getLastName());
		individualProvider.setNamePrefix(individualProviderDto.getNamePrefix());
		individualProvider.setNameSuffix(individualProviderDto.getNameSuffix());
		individualProvider.setCredential(individualProviderDto.getCredential());
		individualProvider.setNpi(individualProviderDto.getNpi());
		individualProvider.setEntityType(individualProviderDto.getEntityType());
		individualProvider.setFirstLineMailingAddress(individualProviderDto.getFirstLineMailingAddress());
		individualProvider.setSecondLineMailingAddress(individualProviderDto.getSecondLineMailingAddress());
		individualProvider.setMailingAddressCityName(individualProviderDto.getMailingAddressCityName());
		individualProvider.setMailingAddressStateName(individualProviderDto.getMailingAddressStateName());
		individualProvider.setMailingAddressPostalCode(individualProviderDto.getMailingAddressPostalCode());
		individualProvider.setMailingAddressCountryCode(individualProviderDto.getMailingAddressCountryCode());
		individualProvider.setMailingAddressTelephoneNumber(individualProviderDto.getMailingAddressTelephoneNumber());
		individualProvider.setMailingAddressFaxNumber(individualProviderDto.getMailingAddressFaxNumber());
		individualProvider.setFirstLinePracticeLocationAddress(individualProviderDto.getFirstLinePracticeLocationAddress());
		individualProvider.setSecondLinePracticeLocationAddress(individualProviderDto.getSecondLinePracticeLocationAddress()); 
		individualProvider.setPracticeLocationAddressCityName(individualProviderDto.getPracticeLocationAddressCityName());
		individualProvider.setPracticeLocationAddressStateName(individualProviderDto.getPracticeLocationAddressStateName());
		individualProvider.setPracticeLocationAddressPostalCode(individualProviderDto.getPracticeLocationAddressPostalCode()); 
		individualProvider.setPracticeLocationAddressCountryCode(individualProviderDto.getPracticeLocationAddressCountryCode()); 
		individualProvider.setPracticeLocationAddressTelephoneNumber(individualProviderDto.getPracticeLocationAddressTelephoneNumber());
		individualProvider.setPracticeLocationAddressFaxNumber(individualProviderDto.getPracticeLocationAddressFaxNumber());
		individualProvider.setEnumerationDate(individualProviderDto.getEnumerationDate());
		individualProvider.setLastUpdateDate(individualProviderDto.getLastUpdateDate());
		individualProvider.setProviderTaxonomyCode(individualProviderDto.getProviderTaxonomyCode());
		individualProvider.setProviderTaxonomyDescription(individualProviderDto.getProviderTaxonomyDescription());
		individualProvider.setPatient(patientRepository.findByUsername(individualProviderDto.getUsername()));

		individualProviderRepository.save(individualProvider);
		
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#deleteIndividualProvider(gov.samhsa.consent2share.domain.provider.IndividualProvider)
	 */
	public void deleteIndividualProvider(IndividualProvider individualProvider) {
        individualProviderRepository.delete(individualProvider);
    }
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#deleteIndividualProviderDto(gov.samhsa.consent2share.service.dto.IndividualProviderDto)
	 */
	public void deleteIndividualProviderDto(IndividualProviderDto individualProviderDto) {
		
		IndividualProvider individualProvider=findIndividualProviderByPatientAndNpi(patientRepository.findByUsername(individualProviderDto.getUsername()),individualProviderDto.getNpi());
		if(individualProvider!=null)
		individualProviderRepository.delete(individualProvider);
		
    }
	
	/**
	 * Find individual provider by npi.
	 *
	 * @param npi the npi
	 * @return the individual provider
	 */
	public IndividualProvider findIndividualProviderByNpi(String npi) {
		return individualProviderRepository.findByNpi(npi);
	}

	/**
	 * Find individual provider by patient and npi.
	 *
	 * @param patient the patient
	 * @param npi the npi
	 * @return the individual provider
	 */
	public IndividualProvider findIndividualProviderByPatientAndNpi(Patient patient,String npi) {
		return individualProviderRepository.findByPatientAndNpi(patient,npi);
	}
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#findIndividualProvider(java.lang.Long)
	 */
	public IndividualProvider findIndividualProvider(Long id) {
        return individualProviderRepository.findOne(id);
    }
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#findAllIndividualProviders()
	 */
	public List<IndividualProvider> findAllIndividualProviders() {
        return individualProviderRepository.findAll();
    }

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#findIndividualProviderEntries(int, int)
	 */
	public List<IndividualProvider> findIndividualProviderEntries(int firstResult, int maxResults) {
        return individualProviderRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#saveIndividualProvider(gov.samhsa.consent2share.domain.provider.IndividualProvider)
	 */
	public void saveIndividualProvider(IndividualProvider individualProvider) {
        individualProviderRepository.save(individualProvider);
    }

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#updateIndividualProvider(gov.samhsa.consent2share.domain.provider.IndividualProvider)
	 */
	public IndividualProvider updateIndividualProvider(IndividualProvider individualProvider) {
        return individualProviderRepository.save(individualProvider);
    }

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#findAllIndividualProvidersDto()
	 */
	@Override
	public List<IndividualProviderDto> findAllIndividualProvidersDto() {
		List<IndividualProviderDto> providers = new ArrayList<IndividualProviderDto>();		
		
		for (IndividualProvider entity : individualProviderRepository.findAll()) {
			providers.add( modelMapper.map(entity, IndividualProviderDto.class));
		}
		 return providers;
	}
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#findIndividualProviderDto(java.lang.Long)
	 */
	@Override
	public IndividualProviderDto findIndividualProviderDto(Long id) {
		IndividualProvider provider = individualProviderRepository.findOne(id);		
		IndividualProviderDto providerDto = modelMapper.map(provider, IndividualProviderDto.class);
		
		return providerDto;
    }

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.provider.IndividualProviderService#findAllStaffIndividualProviders()
	 */
	@Override
	public List<StaffIndividualProvider> findAllStaffIndividualProviders() {
		return staffIndividualProviderRepository.findAll();
	}
}
