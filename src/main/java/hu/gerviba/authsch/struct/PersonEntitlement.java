/**
 *
 * "THE BEER-WARE LICENSE" (Revision 42):
 *
 *  <gerviba@gerviba.hu> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.       Szabó Gergely
 *
 */
package hu.gerviba.authsch.struct;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

/**
 * @author Gerviba
 */
@Getter
public class PersonEntitlement implements Serializable {

    private static final long serialVersionUID = -2904767686389619156L;

    private final long id;
    private final String name;
    private final String status;
    private final String start;
    private final String end;
    private List<String> title;

    public PersonEntitlement(long id, String name, String status, String start, String end) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.start = start;
        this.end = end;
    }

    public PersonEntitlement(long id, String name, String status, String start, String end, List<String> title) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.start = start;
        this.end = end;
        this.title = title;
    }

    @Override
    public String toString() {
        return "PersonEntitlement [id=" + id + ", name=" + name + ", status=" + status + ", start=" + start + ", end="
                + end + ", title=" + title + "]";
    }

}
