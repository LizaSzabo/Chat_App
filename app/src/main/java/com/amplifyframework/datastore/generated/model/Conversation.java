package com.amplifyframework.datastore.generated.model;


import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Conversation type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Conversations")
public final class Conversation implements Model {
  public static final QueryField ID = field("Conversation", "id");
  public static final QueryField MODEL_ID = field("Conversation", "modelId");
  public static final QueryField NAME = field("Conversation", "name");
  public static final QueryField TYPE = field("Conversation", "type");
  public static final QueryField PICTURE = field("Conversation", "picture");
  public static final QueryField FAVOURITE = field("Conversation", "favourite");
  public static final QueryField USERS_ID = field("Conversation", "usersId");
  public static final QueryField MESSAGES_ID = field("Conversation", "messagesId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String modelId;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String", isRequired = true) String type;
  private final @ModelField(targetType="String", isRequired = true) String picture;
  private final @ModelField(targetType="Boolean", isRequired = true) Boolean favourite;
  private final @ModelField(targetType="String") List<String> usersId;
  private final @ModelField(targetType="String") List<String> messagesId;
  public String getId() {
      return id;
  }
  
  public String getModelId() {
      return modelId;
  }
  
  public String getName() {
      return name;
  }
  
  public String getType() {
      return type;
  }
  
  public String getPicture() {
      return picture;
  }
  
  public Boolean getFavourite() {
      return favourite;
  }
  
  public List<String> getUsersId() {
      return usersId;
  }
  
  public List<String> getMessagesId() {
      return messagesId;
  }
  
  private Conversation(String id, String modelId, String name, String type, String picture, Boolean favourite, List<String> usersId, List<String> messagesId) {
    this.id = id;
    this.modelId = modelId;
    this.name = name;
    this.type = type;
    this.picture = picture;
    this.favourite = favourite;
    this.usersId = usersId;
    this.messagesId = messagesId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Conversation conversation = (Conversation) obj;
      return ObjectsCompat.equals(getId(), conversation.getId()) &&
              ObjectsCompat.equals(getModelId(), conversation.getModelId()) &&
              ObjectsCompat.equals(getName(), conversation.getName()) &&
              ObjectsCompat.equals(getType(), conversation.getType()) &&
              ObjectsCompat.equals(getPicture(), conversation.getPicture()) &&
              ObjectsCompat.equals(getFavourite(), conversation.getFavourite()) &&
              ObjectsCompat.equals(getUsersId(), conversation.getUsersId()) &&
              ObjectsCompat.equals(getMessagesId(), conversation.getMessagesId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getModelId())
      .append(getName())
      .append(getType())
      .append(getPicture())
      .append(getFavourite())
      .append(getUsersId())
      .append(getMessagesId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Conversation {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("modelId=" + String.valueOf(getModelId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("type=" + String.valueOf(getType()) + ", ")
      .append("picture=" + String.valueOf(getPicture()) + ", ")
      .append("favourite=" + String.valueOf(getFavourite()) + ", ")
      .append("usersId=" + String.valueOf(getUsersId()) + ", ")
      .append("messagesId=" + String.valueOf(getMessagesId()))
      .append("}")
      .toString();
  }
  
  public static ModelIdStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static Conversation justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Conversation(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      modelId,
      name,
      type,
      picture,
      favourite,
      usersId,
      messagesId);
  }
  public interface ModelIdStep {
    NameStep modelId(String modelId);
  }
  

  public interface NameStep {
    TypeStep name(String name);
  }
  

  public interface TypeStep {
    PictureStep type(String type);
  }
  

  public interface PictureStep {
    FavouriteStep picture(String picture);
  }
  

  public interface FavouriteStep {
    BuildStep favourite(Boolean favourite);
  }
  

  public interface BuildStep {
    Conversation build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep usersId(List<String> usersId);
    BuildStep messagesId(List<String> messagesId);
  }
  

  public static class Builder implements ModelIdStep, NameStep, TypeStep, PictureStep, FavouriteStep, BuildStep {
    private String id;
    private String modelId;
    private String name;
    private String type;
    private String picture;
    private Boolean favourite;
    private List<String> usersId;
    private List<String> messagesId;
    @Override
     public Conversation build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Conversation(
          id,
          modelId,
          name,
          type,
          picture,
          favourite,
          usersId,
          messagesId);
    }
    
    @Override
     public NameStep modelId(String modelId) {
        Objects.requireNonNull(modelId);
        this.modelId = modelId;
        return this;
    }
    
    @Override
     public TypeStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public PictureStep type(String type) {
        Objects.requireNonNull(type);
        this.type = type;
        return this;
    }
    
    @Override
     public FavouriteStep picture(String picture) {
        Objects.requireNonNull(picture);
        this.picture = picture;
        return this;
    }
    
    @Override
     public BuildStep favourite(Boolean favourite) {
        Objects.requireNonNull(favourite);
        this.favourite = favourite;
        return this;
    }
    
    @Override
     public BuildStep usersId(List<String> usersId) {
        this.usersId = usersId;
        return this;
    }
    
    @Override
     public BuildStep messagesId(List<String> messagesId) {
        this.messagesId = messagesId;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String modelId, String name, String type, String picture, Boolean favourite, List<String> usersId, List<String> messagesId) {
      super.id(id);
      super.modelId(modelId)
        .name(name)
        .type(type)
        .picture(picture)
        .favourite(favourite)
        .usersId(usersId)
        .messagesId(messagesId);
    }
    
    @Override
     public CopyOfBuilder modelId(String modelId) {
      return (CopyOfBuilder) super.modelId(modelId);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder type(String type) {
      return (CopyOfBuilder) super.type(type);
    }
    
    @Override
     public CopyOfBuilder picture(String picture) {
      return (CopyOfBuilder) super.picture(picture);
    }
    
    @Override
     public CopyOfBuilder favourite(Boolean favourite) {
      return (CopyOfBuilder) super.favourite(favourite);
    }
    
    @Override
     public CopyOfBuilder usersId(List<String> usersId) {
      return (CopyOfBuilder) super.usersId(usersId);
    }
    
    @Override
     public CopyOfBuilder messagesId(List<String> messagesId) {
      return (CopyOfBuilder) super.messagesId(messagesId);
    }
  }
  
}
