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

/** This is an auto generated class representing the User type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Users")
public final class User implements Model {
  public static final QueryField ID = field("User", "id");
  public static final QueryField MODEL_ID = field("User", "modelId");
  public static final QueryField USER_NAME = field("User", "userName");
  public static final QueryField PASSWORD = field("User", "password");
  public static final QueryField PROFILE_PICTURE = field("User", "profilePicture");
  public static final QueryField CONVERSATIONS = field("User", "conversations");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String modelId;
  private final @ModelField(targetType="String", isRequired = true) String userName;
  private final @ModelField(targetType="String", isRequired = true) String password;
  private final @ModelField(targetType="String", isRequired = true) String profilePicture;
  private final @ModelField(targetType="String") List<String> conversations;
  public String getId() {
      return id;
  }
  
  public String getModelId() {
      return modelId;
  }
  
  public String getUserName() {
      return userName;
  }
  
  public String getPassword() {
      return password;
  }
  
  public String getProfilePicture() {
      return profilePicture;
  }
  
  public List<String> getConversations() {
      return conversations;
  }
  
  private User(String id, String modelId, String userName, String password, String profilePicture, List<String> conversations) {
    this.id = id;
    this.modelId = modelId;
    this.userName = userName;
    this.password = password;
    this.profilePicture = profilePicture;
    this.conversations = conversations;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      User user = (User) obj;
      return ObjectsCompat.equals(getId(), user.getId()) &&
              ObjectsCompat.equals(getModelId(), user.getModelId()) &&
              ObjectsCompat.equals(getUserName(), user.getUserName()) &&
              ObjectsCompat.equals(getPassword(), user.getPassword()) &&
              ObjectsCompat.equals(getProfilePicture(), user.getProfilePicture()) &&
              ObjectsCompat.equals(getConversations(), user.getConversations());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getModelId())
      .append(getUserName())
      .append(getPassword())
      .append(getProfilePicture())
      .append(getConversations())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("User {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("modelId=" + String.valueOf(getModelId()) + ", ")
      .append("userName=" + String.valueOf(getUserName()) + ", ")
      .append("password=" + String.valueOf(getPassword()) + ", ")
      .append("profilePicture=" + String.valueOf(getProfilePicture()) + ", ")
      .append("conversations=" + String.valueOf(getConversations()))
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
  public static User justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new User(
      id,
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
      userName,
      password,
      profilePicture,
      conversations);
  }
  public interface ModelIdStep {
    UserNameStep modelId(String modelId);
  }
  

  public interface UserNameStep {
    PasswordStep userName(String userName);
  }
  

  public interface PasswordStep {
    ProfilePictureStep password(String password);
  }
  

  public interface ProfilePictureStep {
    BuildStep profilePicture(String profilePicture);
  }
  

  public interface BuildStep {
    User build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep conversations(List<String> conversations);
  }
  

  public static class Builder implements ModelIdStep, UserNameStep, PasswordStep, ProfilePictureStep, BuildStep {
    private String id;
    private String modelId;
    private String userName;
    private String password;
    private String profilePicture;
    private List<String> conversations;
    @Override
     public User build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new User(
          id,
          modelId,
          userName,
          password,
          profilePicture,
          conversations);
    }
    
    @Override
     public UserNameStep modelId(String modelId) {
        Objects.requireNonNull(modelId);
        this.modelId = modelId;
        return this;
    }
    
    @Override
     public PasswordStep userName(String userName) {
        Objects.requireNonNull(userName);
        this.userName = userName;
        return this;
    }
    
    @Override
     public ProfilePictureStep password(String password) {
        Objects.requireNonNull(password);
        this.password = password;
        return this;
    }
    
    @Override
     public BuildStep profilePicture(String profilePicture) {
        Objects.requireNonNull(profilePicture);
        this.profilePicture = profilePicture;
        return this;
    }
    
    @Override
     public BuildStep conversations(List<String> conversations) {
        this.conversations = conversations;
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
    private CopyOfBuilder(String id, String modelId, String userName, String password, String profilePicture, List<String> conversations) {
      super.id(id);
      super.modelId(modelId)
        .userName(userName)
        .password(password)
        .profilePicture(profilePicture)
        .conversations(conversations);
    }
    
    @Override
     public CopyOfBuilder modelId(String modelId) {
      return (CopyOfBuilder) super.modelId(modelId);
    }
    
    @Override
     public CopyOfBuilder userName(String userName) {
      return (CopyOfBuilder) super.userName(userName);
    }
    
    @Override
     public CopyOfBuilder password(String password) {
      return (CopyOfBuilder) super.password(password);
    }
    
    @Override
     public CopyOfBuilder profilePicture(String profilePicture) {
      return (CopyOfBuilder) super.profilePicture(profilePicture);
    }
    
    @Override
     public CopyOfBuilder conversations(List<String> conversations) {
      return (CopyOfBuilder) super.conversations(conversations);
    }
  }
  
}
