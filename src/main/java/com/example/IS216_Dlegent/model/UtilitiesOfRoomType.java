package com.example.IS216_Dlegent.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TIEN_ICH_PHONG")
@IdClass(UtilitiesOfRoomType.UtilitiesOfRoomTypeId.class)
public class UtilitiesOfRoomType {

    @Id
    @ManyToOne
    @JoinColumn(name = "ID_PHONG", nullable = false)
    private LoaiPhong loaiPhong;

    @Id
    @ManyToOne
    @JoinColumn(name = "ID_TIEN_ICH", nullable = false)
    private Utilities utility;

    public UtilitiesOfRoomType() {
    }

    public UtilitiesOfRoomType(LoaiPhong loaiPhong, Utilities utility) {
        this.loaiPhong = loaiPhong;
        this.utility = utility;
    }

    public LoaiPhong getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(LoaiPhong loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public Utilities getUtility() {
        return utility;
    }

    public void setUtility(Utilities utility) {
        this.utility = utility;
    }

    // Composite key class
    public static class UtilitiesOfRoomTypeId implements Serializable {
        private static final long serialVersionUID = 1L;

        private Long loaiPhong;
        private Long utility;

        public UtilitiesOfRoomTypeId() {
        }

        public UtilitiesOfRoomTypeId(Long loaiPhong, Long utility) {
            this.loaiPhong = loaiPhong;
            this.utility = utility;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            UtilitiesOfRoomTypeId that = (UtilitiesOfRoomTypeId) o;

            if (!loaiPhong.equals(that.loaiPhong))
                return false;
            return utility.equals(that.utility);
        }

        @Override
        public int hashCode() {
            int result = loaiPhong.hashCode();
            result = 31 * result + utility.hashCode();
            return result;
        }
    }
}
