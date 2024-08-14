package domain;

import java.io.Serializable;

public enum ReportState implements Serializable {
	TO_HANDLE, HANDLING, HANDLED, SHADOWED;
}
