package net.gameslabs.model;

/**
 * Generic custom Exception to be thrown to signify a failed assignment.
 */
public class AssignmentFailed extends RuntimeException {
  public AssignmentFailed(String info) {
    super(info);
  }
}
