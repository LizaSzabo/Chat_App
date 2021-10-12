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

/** This is an auto generated class representing the Message type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Messages")
public final class Message implements Model {
  public static final QueryField ID = field("Message", "id");
  public static final QueryField MODEL_ID = field("Message", "modelId");
  public static final QueryField SENDER = field("Message", "sender");
  public static final QueryField CONTENT = field("Message", "content");
  public static final QueryField DATE = field("Message", "date");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String modelId;
  private final @ModelField(targetType="String", isRequired = true) String sender;
  private final @ModelField(targetType="String", isRequired = true) String content;
  private final @ModelField(targetType="String", isRequired = true) String date;
  public String getId() {
      return id;
  }
  
  public String getModelId() {
      return modelId;
  }
  
  public String getSender() {
      return sender;
  }
  
  public String getContent() {
      return content;
  }
  
  public String getDate() {
      return date;
  }
  
  private Message(String id, String modelId, String sender, String content, String date) {
    this.id = id;
    this.modelId = modelId;
    this.sender = sender;
    this.content = content;
    this.date = date;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Message message = (Message) obj;
      return ObjectsCompat.equals(getId(), message.getId()) &&
              ObjectsCompat.equals(getModelId(), message.getModelId()) &&
              ObjectsCompat.equals(getSender(), message.getSender()) &&
              ObjectsCompat.equals(getContent(), message.getContent()) &&
              ObjectsCompat.equals(getDate(), message.getDate());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getModelId())
      .append(getSender())
      .append(getContent())
      .append(getDate())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Message {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("modelId=" + String.valueOf(getModelId()) + ", ")
      .append("sender=" + String.valueOf(getSender()) + ", ")
      .append("content=" + String.valueOf(getContent()) + ", ")
      .append("date=" + String.valueOf(getDate()))
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
  public static Message justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Message(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      modelId,
      sender,
      content,
      date);
  }
  public interface ModelIdStep {
    SenderStep modelId(String modelId);
  }
  

  public interface SenderStep {
    ContentStep sender(String sender);
  }
  

  public interface ContentStep {
    DateStep content(String content);
  }
  

  public interface DateStep {
    BuildStep date(String date);
  }
  

  public interface BuildStep {
    Message build();
    BuildStep id(String id) throws IllegalArgumentException;
  }
  

  public static class Builder implements ModelIdStep, SenderStep, ContentStep, DateStep, BuildStep {
    private String id;
    private String modelId;
    private String sender;
    private String content;
    private String date;
    @Override
     public Message build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Message(
          id,
          modelId,
          sender,
          content,
          date);
    }
    
    @Override
     public SenderStep modelId(String modelId) {
        Objects.requireNonNull(modelId);
        this.modelId = modelId;
        return this;
    }
    
    @Override
     public ContentStep sender(String sender) {
        Objects.requireNonNull(sender);
        this.sender = sender;
        return this;
    }
    
    @Override
     public DateStep content(String content) {
        Objects.requireNonNull(content);
        this.content = content;
        return this;
    }
    
    @Override
     public BuildStep date(String date) {
        Objects.requireNonNull(date);
        this.date = date;
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
    private CopyOfBuilder(String id, String modelId, String sender, String content, String date) {
      super.id(id);
      super.modelId(modelId)
        .sender(sender)
        .content(content)
        .date(date);
    }
    
    @Override
     public CopyOfBuilder modelId(String modelId) {
      return (CopyOfBuilder) super.modelId(modelId);
    }
    
    @Override
     public CopyOfBuilder sender(String sender) {
      return (CopyOfBuilder) super.sender(sender);
    }
    
    @Override
     public CopyOfBuilder content(String content) {
      return (CopyOfBuilder) super.content(content);
    }
    
    @Override
     public CopyOfBuilder date(String date) {
      return (CopyOfBuilder) super.date(date);
    }
  }
  
}
