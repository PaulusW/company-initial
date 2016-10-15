package io.rscale.training.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class SampleData implements CommandLineRunner {

    private final CompanyRepository repository;

    @Autowired
    public SampleData(CompanyRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) throws Exception {	 
    	add(new Company("Resilient Scale", new String[]{"Resilient Scale, LLC", "Resilient Scale LLC", "RScale"}, "https://rscale.io", "resilientscale"));
    	add(new Company("Biarca", null, "https://biarca.io", null));
        repository.findAll().forEach(System.out::println);
    }
    	
    private void add(Company company) throws Exception {
    	if (repository.findByNameIgnoreCase(company.getName()).isEmpty()) {
    		repository.save(company);
    	}
    }
    
}