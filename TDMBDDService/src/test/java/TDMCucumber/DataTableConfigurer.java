package TDMCucumber;
import java.util.Locale;
import java.util.Map;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableEntryTransformer;

import com.ait.callData.EventCause;
import com.ait.callData.FailureClass;
import com.ait.callData.UE;
import com.ait.callData.MccMnc;;


/*
 * Maps datatables in feature files to custom domain objects.
 */
public class DataTableConfigurer implements TypeRegistryConfigurer {

    @Override
    public Locale locale() {
        return Locale.ENGLISH;
    }

    @Override
    public void configureTypeRegistry(TypeRegistry registry) {
  
        registry.defineDataTableType(new DataTableType(EventCause.class, new TableEntryTransformer<EventCause>() {
            @Override
            public EventCause transform(Map<String, String> entry) {
                return new EventCause(Integer.parseInt(entry.get("cause_code")), Integer.parseInt(entry.get("event_id")), entry.get("description"));
            }
        }));
    
    
        registry.defineDataTableType(new DataTableType(FailureClass.class, new TableEntryTransformer<FailureClass>() {
        	@Override
        	public FailureClass transform(Map<String, String> entry) {
        		return new FailureClass(Integer.parseInt(entry.get("failure_class")), entry.get("description"));
        	}
        }));
    
        registry.defineDataTableType(new DataTableType(UE.class, new TableEntryTransformer<UE>() {
        	@Override
        	public UE transform(Map<String, String> entry) {
        		return new UE(
        				Integer.parseInt(entry.get("ue_type")), 
        				"DummyMarketingName",
        				"DummyManufacturer",
        				"Dummyaccess_capability",
        				"Dummymodel",
        				"Dummyvendor_name",
        				"Dummyue_type",
        				"Dummyos",
        				"Dummyinput_mode"
        				);
        	}    	
    	}));

        registry.defineDataTableType(new DataTableType(MccMnc.class, new TableEntryTransformer<MccMnc>() {
            @Override
            public MccMnc transform(Map<String, String> entry) {
                return new MccMnc(Integer.parseInt(entry.get("Market")), Integer.parseInt(entry.get("Operator")), entry.get("Country"),entry.get("OperatorName"));
            }
        }));
       
    }
}


