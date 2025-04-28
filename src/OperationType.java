import java.util.ArrayList;
import java.util.List;

public class OperationType {
    String name;
    int duration;
    String requiredResource;

    OperationType(String n, int d){
        name=n;
        duration=d;
    }

    void addRequiredResource(String resource){
        requiredResource=resource;
    }

    public String getName(){
        return name;
    }

    public Resource [] getRequiredResources(List<Resource> allResource){

        List<Resource> required=new ArrayList<>();
        for(Resource resource:allResource){

            if(resource.id.equals(requiredResource))
                required.add(resource);
        }
        return required.toArray(new Resource[0]);
    }
}
