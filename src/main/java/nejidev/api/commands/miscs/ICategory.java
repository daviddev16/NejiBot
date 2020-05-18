package nejidev.api.commands.miscs;

@CommandCategory(category = Category.SERVER)
public interface ICategory {

    default Category getCommandCategory(){
        CommandCategory category = null;
        try {
             category = getClass().getAnnotationsByType(CommandCategory.class)[0];
        }catch (Exception e){ }
        if(category == null){
            return Category.SERVER;
        }
        return category.category();


    }
}
